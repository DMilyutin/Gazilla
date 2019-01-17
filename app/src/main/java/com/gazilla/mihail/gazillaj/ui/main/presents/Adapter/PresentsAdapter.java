package com.gazilla.mihail.gazillaj.ui.main.presents.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gazilla.mihail.gazillaj.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.POJO.Success;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.presentation.main.presents.PresentAdapterView;
import com.gazilla.mihail.gazillaj.presentation.main.presents.PresentsPresenter;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.MenuImg;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.ImgCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.StaticCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class PresentsAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<MenuCategory> menuCategories;
    private List<ImgGazilla> imgGazillas;
    private MenuImg menuImg;
    private int[] favorite;
    private ImageLoader imageLoader;

    //private ImageView imgFavorit;


    @SuppressLint("UseSparseArrays")
    public PresentsAdapter(Context context, List<MenuCategory> menuCategories, List<ImgGazilla> imgGazillas) {
        this.context = context;
        this.menuCategories = menuCategories;
        this.imgGazillas = imgGazillas;
        favorite = init2(menuCategories);
        menuImg = new MenuImg();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }



    private int[] init2(List<MenuCategory> categories){
        int[] favor = Initialization.userWithKeys.getFavorites();  // список id которые в любимом


        int max = categories.get(categories.size()-1).getItems().get(categories.get(categories.size()-1).getItems().size()-1).getId();
        Log.i("Loog" ," max = categories - " + max);
        int[] mapFavorit = new int[max+1];

        for (int z = 0; z<max+1;z++)
            mapFavorit[z]=0;


        for(int iCategories = 0; iCategories < categories.size(); iCategories++ ){

            for(int iItem = 0; iItem<categories.get(iCategories).getItems().size(); iItem++){

                int id = categories.get(iCategories).getItems().get(iItem).getId();

                for (int i = 0; i<favor.length;i++){
                    if(favor[i]==id)
                        mapFavorit[id]=1;
                }

            }
        }
        for (int it: mapFavorit) {
            Log.i("Loog" ,"return mapFavorit " + it);
        }
        return mapFavorit;
    }

    @Override
    public int getGroupCount() {
        return menuCategories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return menuCategories.get(groupPosition).getItems().size();
    }

    @Override
    public MenuCategory getGroup(int groupPosition) {
        return menuCategories.get(groupPosition);
    }

    @Override
    public MenuItem getChild(int groupPosition, int childPosition) {
        return menuCategories.get(groupPosition).getItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return menuCategories.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return menuCategories.get(groupPosition).getItems().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.for_exlist_name_group_presents, null);
        }

        ((TextView) convertView.findViewById(R.id.tvNameGroupPresentsExList)).setText(getGroup(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.for_exlist_name_child_group_presents, null);
        }



        MenuItem menuItem = getChild(groupPosition, childPosition);
        Log.i("Loog", "id menu item - " + menuItem.getId());

        int favor;
        if (favorite[menuItem.getId()]==1)
           favor = R.drawable.ic_grade_gold_24dp;
       else
            favor = R.drawable.ic_grade_grey24dp;

        ((ImageView) convertView.findViewById(R.id.imgMiniItemMemu)).setImageResource(R.drawable.gaz);
        ((ImageView) convertView.findViewById(R.id.imgFavoritIcon)).setImageResource(favor);
        View finalConvertView1 = convertView;

        ((ImageView) convertView.findViewById(R.id.imgFavoritIcon)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Loog" , "Нажали на фаворит! сейчас статус - " + favorite);
                //imgFavorit.setClickable(false);
                if (favorite[menuItem.getId()]==1) {
                    // убрать из любимого
                    Log.i("Loog" , "из любимого" );
                    delFavoritMenu(menuItem.getId(), (ImageView) finalConvertView1.findViewById(R.id.imgFavoritIcon));

                }
                else {
                    // добавить в любимое
                    Log.i("Loog" , "в любимое" );
                    addFavoritMenu(menuItem.getId(),(ImageView) finalConvertView1.findViewById(R.id.imgFavoritIcon));
                }
            }
        });

        /*for (ImgGazilla img: imgGazillas) {
            if (menuItem.getId()==img.getId())
                if (Initialization.imgGazillas.get(menuItem.getId()).getImage()!=null){
                    byte[] b = Initialization.imgGazillas.get(menuItem.getId()).getImage();
                    Bitmap bmp = BitmapFactory.decodeStream(new ByteArrayInputStream(b));
                    ((ImageView) finalConvertView1.findViewById(R.id.imgMiniItemMemu)).setImageBitmap(bmp);
                }
        }*/
        if(menuImg.getImg(menuItem.getId())!=0){
        String res = "drawable://" + menuImg.getImg(menuItem.getId());

        //((ImageView) finalConvertView1.findViewById(R.id.imgMiniItemMemu)).setImageResource(res);
        imageLoader.displayImage(res, ((ImageView)finalConvertView1.findViewById(R.id.imgMiniItemMemu)));
        }


        ((TextView) convertView.findViewById(R.id.tvNameChildPresentsExList)).setText(menuItem.getName());
        ((TextView) convertView.findViewById(R.id.tvDescriptionChildPresentsExList)).setText(menuItem.getDescription());
        ((TextView) convertView.findViewById(R.id.tvCoastChildPresentsExList)).setText(String.valueOf(menuItem.getPrice()));


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    private void setRedCalor(int id, ImageView imageView) {
        favorite[id] = 1;


        imageView.setImageResource(R.drawable.ic_grade_gold_24dp);
        Toast.makeText(context, "Товар добавлен в избранное", Toast.LENGTH_LONG).show();
        //imgFavorit.setClickable(true);
    }


    private void setGreyColor(int id, ImageView imageView) {


        favorite[id] = 0;

        imageView.setImageResource(R.drawable.ic_grade_grey24dp);
        Toast.makeText(context, "Товар удален из избранного", Toast.LENGTH_LONG).show();
        //imgFavorit.setClickable(true);
    }

    private void addFavoritMenu(int id, ImageView imageView){


        Initialization.repositoryApi.addFavoriteItem(Initialization.userWithKeys.getPublickey(), id,
                signatur(id), new SuccessCallBack() {
                    @Override
                    public void reservResponse(Success success) {
                        setRedCalor(id,imageView);
                    }

                    @Override
                    public void errorResponse(String error) {
                        Log.i("Loog" , "добавить/ ошибка - " + error);
                    }
                }, new FailCallBack() {
                    @Override
                    public void setError(Throwable throwable) {
                        Log.i("Loog" , "удалить/ошибкаТ" + throwable.getMessage());
                    }
                });
    }

    private void delFavoritMenu(int id, ImageView imageView){
        Initialization.repositoryApi.delFavoritItem(Initialization.userWithKeys.getPublickey(), id,
                signatur(id), new SuccessCallBack() {
                    @Override
                    public void reservResponse(Success success) {
                        setGreyColor(id, imageView);
                    }

                    @Override
                    public void errorResponse(String error) {
                        Log.i("Loog" , "удалить/ошибка" + error);
                    }
                }, new FailCallBack() {
                    @Override
                    public void setError(Throwable throwable) {
                        Log.i("Loog" , "удалить/ошибкаТ" + throwable.getMessage());
                    }
                });
    }

    private String signatur( int id){
        String dat = "id="+id;

        return Initialization.signatur(Initialization.userWithKeys.getPrivatekey(),  dat);
    }
}

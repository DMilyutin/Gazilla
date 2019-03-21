package com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor;

import android.util.Log;

import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.StaticCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class PhotoMemuInterator {

    private List<ImgGazilla> imgGazillass;
    private int count = 0;

    void startInitPhotoMenu(List<MenuCategory> categories){
        Log.i("Loog", "hasConnection");
        imgGazillass = new ArrayList<>();
        donloadPhotoFromServer(categories);
    }

    private void donloadPhotoFromServer(List<MenuCategory> categories) {
        Log.i("Loog", "hasConnection");

        int max = categories.get(categories.size() - 1).getItems().get(categories.get(categories.size() - 1).getItems().size() - 1).getId();
        Log.i("Loog", " max = categories - " + max);


        for (int iCategories = 0; iCategories < categories.size(); iCategories++) {

            for (int iItem = 0; iItem < categories.get(iCategories).getItems().size(); iItem++) {

                int id = categories.get(iCategories).getItems().get(iItem).getId();

                InitializationAp.getInstance().getRepositoryApi().getStaticFromServer("menu", String.valueOf(id), new StaticCallBack() {
                    @Override
                    public void myStatic(ResponseBody responseBody) throws IOException {
                        if(responseBody!=null) {
                            ImgGazilla imgGazilla = new ImgGazilla(id, responseBody.bytes());
                            addImgInList(imgGazilla, id, max);
                            addCount();
                        }
                    }

                    @Override
                    public void showError(String error) {
                        Log.i("Loog", "Нет картинки id - "+ id +" err -" + error);
                        addCount();
                    }
                }, new FailCallBack() {
                    @Override
                    public void setError(Throwable throwable) {

                    }
                });
            }
        }

        //savePhotoOnDB(imgGazillass);
    }

    //Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
    private void savePhotoOnDB(){

    }

    private void addImgInList(ImgGazilla imgGazilla, int id, int max){
        imgGazillass.add(imgGazilla);
        Log.i("Loog", "addImgInList isEmpty"+ imgGazillass.isEmpty());
        if (count>=max){
            savePhotoOnDB();
            Log.i("Loog", "save photo on DB");
        }
    }

    private void addCount(){
        count++;
    }
}

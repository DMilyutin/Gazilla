package com.gazilla.mihail.gazillaj.presentation.main.card;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.MenuImg;
import com.gazilla.mihail.gazillaj.utils.POJO.DragonWheel;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuDB;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.utils.POJO.QTY;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.CardInteractor;
import com.gazilla.mihail.gazillaj.model.repository.MenuAdapter.MenuAdapterApiDb;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.QRcode;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LevelsCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuDBCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.QTYCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.WheelCallBack;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.util.List;
import java.util.Map;

public class CardPresenter {

    private int showFirstDialog = 0;

    private CardView cardView;
    private CardInteractor cardInteractor;
    private MenuImg menuImg;
    private Context context;
    private SharedPref sharedPref;
    private DragonWheel dragonWheel;

    private int mySpins = 0;

    public CardPresenter(CardView cardView, Context context) {
        this.cardView = cardView;
        this.context = context;
        cardInteractor = new CardInteractor(context);
        menuImg = new MenuImg();
        sharedPref = new SharedPref(context);
    }

    public void initCardFragment(){
        if (showFirstDialog == 0){
            firstStartApp(sharedPref.getFirstStart());
            showFirstDialog++;
        }
        myProgress();
        mySpins();
        myIdForQRcode();
        initRuletca();
    }

    private void initRuletca(){
        int myLvl = Initialization.userWithKeys.getLevel();
        int res = R.drawable.koleso1;
        switch (myLvl){
            case 2: {
                res = R.drawable.koleso2;
                break;
            }
            case 3: {
                res = R.drawable.koleso3;
                break;
            }
            case 4: {
                res = R.drawable.koleso4;
                break;
            }
            case 5: {
                res = R.drawable.koleso5;
                break;
            }
        }
        cardView.initLvlForKoleso(res);
    }

    private void myIdForQRcode()  {

        String id = myId();
        Log.i("Loog", "my id - " + id);
        try {
            Bitmap bitmap = QRcode.encodeAsBitmap(id, BarcodeFormat.QR_CODE, 250, 250);
            cardView.setQRcode(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    //_____________________________уровни и прогресс_______________________________________

    public void myProgress(){
        Log.i("Loog", "начало обновления прогреса");

        int sum = Initialization.userWithKeys.getSum();
        int myLvl = Initialization.userWithKeys.getLevel();
        Log.i("Loog", "myLvl - " + myLvl);


        try {
            cardInteractor.level(new LevelsCallBack() {

                @Override
                public void levelsFromSerser(Map<Integer, Integer> levels) {
                    Log.i("Loog", "levels : " + levels.get(myLvl));
                    if (myLvl==5)
                        cardView.setValueProgressBar(levels.get(5), sum);
                    else
                        cardView.setValueProgressBar(levels.get(myLvl+1), sum);
                    cardView.initListWithLvl(myLvl, levels);

                }

                @Override
                public void errorLevels(String error) {
                    new BugReport().sendBugInfo(error, "InitInteractor.userData.setError.Throwable");
                }
            }, new FailCallBack() {
                @Override
                public void setError(Throwable throwable) {
                    new BugReport().sendBugInfo(throwable.getMessage(), "CardPresenter.myProgress.setError.Throwable");
                }
            });
        }catch (NullPointerException ex){
            new BugReport().sendBugInfo(ex.getMessage(), "CardPresenter.myProgress.levelsFromSerser.NullPointerException");
        }






    }

    private String myId(){
        return cardInteractor.getMyId();
    }

    public void mySpins(){
        cardInteractor.mySpins(qty -> {
            mySpins = qty.getQty();
            cardView.setSpins(qty.getQty());
            },
                throwable -> {
                    mySpins = 0;
                    cardView.setSpins(0);
                    new BugReport().sendBugInfo(throwable.getMessage(), "CardPresenter.mySpins.setError");
                });

    }

    private void firstStartApp(Boolean firs){
        if (firs)
            cardView.firstDialogTip();
    }

    //________________________________Прокрутка колеса дракона__________________________

    public void prepearWheel(){
        if (mySpins>0)
            wheeling();
    }

    private void wheeling(){
        cardView.startWheeling();
        cardInteractor.wheeling(new WheelCallBack() {
            @Override
            public void myWin(DragonWheel wheel) {
                dragonWheel = wheel;
            }

            @Override
            public void winError(String error) {
                new BugReport().sendBugInfo(error, "CardPresenter.wheeling.winError");
            }

        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                new BugReport().sendBugInfo(throwable.getMessage(), "CardPresenter.wheeling.setError.Throwable");
                Log.i("Loog", "Колесо дракона ошибка T-" + throwable.getMessage());
            }
        });
    }

    public void myWinn(){
        initCardFragment();
        if (dragonWheel!=null) {
            if (dragonWheel.getWinType().equals("point")){
                cardView.myWin(String.valueOf(dragonWheel.getId()) + " баллов", "drawable://" + menuImg.getImg(0));
                initCardFragment();
            }
            else
                winById(dragonWheel.getId());
        }
        else
            new AppDialogs().warningDialog(context, "Плохое интернет соединение");
    }

    //__________________________________поиск item по id_____________________________________

    private void winById(int id){
        MenuAdapterApiDb menuAdapterApiDb = new MenuAdapterApiDb();
        Initialization.repositoryDB.menuFromDB(new MenuDBCallBack() {
            @Override
            public void ollMenu(List<MenuDB> menuDBList) {
                List<MenuCategory> categories = menuAdapterApiDb.fromMenuDB(menuDBList);
                findItemById(categories, id);
            }

            @Override
            public void showError(String error) {
                new BugReport().sendBugInfo(error, "CardPresenter.winById.showError");
            }
        });
    }

    private void findItemById(List<MenuCategory> categories, int id){

        for(int iCategorys = 0; iCategorys<categories.size(); iCategorys++){

            for(int iItems = 0; iItems<categories.get(iCategorys).getItems().size(); iItems++){

                if(categories.get(iCategorys).getItems().get(iItems).getId() == id){
                    MenuItem item = categories.get(iCategorys).getItems().get(iItems);
                    Log.i("Loog", "Колесо дракона показ выиграша - gift = " + item.getName());
                    cardView.myWin(item.getName(),"drawable://"+menuImg.getImg(0));
                    initCardFragment();
                }
            }
        }
    }

    public void testShowWin(){
       // DragonWheel wheel = new DragonWheel(95, "gift");
        //myWinn(wheel);
    }
}

package com.gazilla.mihail.gazillaj.presentation.main.card;

import android.graphics.Bitmap;
import android.util.Log;

import com.gazilla.mihail.gazillaj.POJO.DragonWheel;
import com.gazilla.mihail.gazillaj.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.POJO.MenuDB;
import com.gazilla.mihail.gazillaj.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.POJO.QTY;
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

    private CardView cardView;
    private CardInteractor cardInteractor;

    public CardPresenter(CardView cardView, CardInteractor cardInteractor) {
        this.cardView = cardView;
        this.cardInteractor = cardInteractor;
    }

    public void initRuletca(){
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

    public void idClientForQRcode()  {

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





        //if(myLvl == 0){maxValue = 10000;}
        //if(myLvl == 1){maxValue = 30000;}
        //if(myLvl == menu_id_2){maxValue = 100000;}
        //if(myLvl == 3){maxValue = 300000;}
        //if(myLvl == 4){maxValue = 300000;}



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

            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {

            }
        });




    }

    private String myId(){
        return cardInteractor.getMyId();
    }

    public void mySpins(){
        cardInteractor.mySpins(new QTYCallBack() {
            @Override
            public void myQTY(QTY qty) {
                cardView.setSpins(qty.getQty());
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {

            }
        });
    }

    //________________________________Прокрутка колеса дракона__________________________

    public void wheeling(){
        cardInteractor.wheeling(new WheelCallBack() {
            @Override
            public void myWin(DragonWheel wheel) {
                myWinn(wheel);
            }

            @Override
            public void winError(String error) {
                Log.i("Loog", "Колесо дракона ошибка -" + error);
            }

        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "Колесо дракона ошибка T-" + throwable.getMessage());
            }
        });
    }

    private void myWinn(DragonWheel wheel){
        if(wheel.getWinType().equals("point")){
            Log.i("Loog", "Колесо дракона показ выиграша - point = " + wheel.getId());
           cardView.myWin("point", String.valueOf(wheel.getId()) + " баллов", 0, null);
        }
        else
        {
            winById(wheel.getId());
            //String nameItem = null; // дость из бд

        }
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
            public void showError(int error) {

            }
        });
    }

    private void findItemById(List<MenuCategory> categories, int id){

        for(int iCategorys = 0; iCategorys<categories.size(); iCategorys++){

            for(int iItems = 0; iItems<categories.get(iCategorys).getItems().size(); iItems++){
                if(categories.get(iCategorys).getItems().get(iItems).getId() == id){
                    MenuItem item = categories.get(iCategorys).getItems().get(iItems);
                    Log.i("Loog", "Колесо дракона показ выиграша - gift = " + item.getName());
                    cardView.myWin("gift", item.getName(),id, null);
                }
            }
        }
    }
}

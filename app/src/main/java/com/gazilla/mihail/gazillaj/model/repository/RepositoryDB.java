package com.gazilla.mihail.gazillaj.model.repository;

import com.gazilla.mihail.gazillaj.utils.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuDB;
import com.gazilla.mihail.gazillaj.utils.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.model.data.db.DAO.ImgGazillaDao;
import com.gazilla.mihail.gazillaj.model.data.db.DAO.MenuDBDao;
import com.gazilla.mihail.gazillaj.model.data.db.DAO.PromoDao;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.ImgCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuDBCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PromoCallBack;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RepositoryDB {

    private PromoDao promoDao = Initialization.appDatabase.promoDao();
    private MenuDBDao menuDBDao = Initialization.appDatabase.menuDBDao();
    private ImgGazillaDao imgGazillaDao = Initialization.appDatabase.imgDao();

    // ________________________ запросы в бд Меню __________________________________________

    public void menuFromDB(MenuDBCallBack menuCallBack) {

        Observable.fromCallable(new CallableMenuFromDB(menuDBDao))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menuCallBack::ollMenu);



    }

    public void newMenuFromServer(List<MenuDB> menuDBList){
        Observable.fromCallable(new CallableNewMenuFromServer(menuDBDao, menuDBList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    public void clearMenuTable(){
        Observable.fromCallable(new CallableClearMenuTable(menuDBDao))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }


    //________________________ запросы в бд картинки

    public void imgFromBD(ImgCallBack imgCallBack){
        Observable.fromCallable(new CallableImgMenuFromDB(imgGazillaDao))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imgGazillaList -> imgCallBack.ollImgFromDB(imgGazillaList));

    }

    public void loadImgOnDB(List<ImgGazilla> imgGazillas){
        Observable.fromCallable(new CallableLoadImgOnDB(imgGazillaDao, imgGazillas))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }
    public void loadOneImg(ImgGazilla imgGazilla){
        Observable.fromCallable(new CallableLoadOneImg(imgGazillaDao, imgGazilla))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void clearTableImg(){
        Observable.fromCallable(new CallableClearTableImg(imgGazillaDao))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void imgByIdFromDb(int id, ImgCallBack imgCallBack){
        Observable.fromCallable(new CallableImgById(imgGazillaDao, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imgGazilla -> {imgCallBack.imgById(imgGazilla);});

    }

    //________________________ запросы в бд промо________________________________________

    public void promoFromDB(PromoCallBack promoCallBack){


        Observable.fromCallable(new CallablePromoDB(promoDao))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(promoCallBack::myPromo, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ;
                    }
                });

    }

    /*public void addNewPromo(PromoItem promoItem){
        Observable.fromCallable(new AddNewPromo(promoDao, promoItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

    public void loadOllNewPromo(List<PromoItem> promoItemList){
        Observable.fromCallable(new CallableUpdatePromoDB(promoDao, promoItemList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void clearPromoTable(){
        Observable.fromCallable(new CallableClearPromoTable(promoDao))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}

//______________________________ меню Callable____________________________________________

class CallableMenuFromDB  implements Callable<List<MenuDB>> {

    private MenuDBDao menuDBDao;

    CallableMenuFromDB(MenuDBDao menuDBDao) {
        this.menuDBDao = menuDBDao;
    }

    @Override
    public List<MenuDB> call() throws Exception {
        return menuDBDao.getOllMenuItemDB();
    }
}

class CallableNewMenuFromServer implements Callable<Boolean> {

    private MenuDBDao menuDBDao;
    private List<MenuDB> menuDBList;

    CallableNewMenuFromServer(MenuDBDao menuDBDao, List<MenuDB> menuDBList) {
        this.menuDBDao = menuDBDao;
        this.menuDBList = menuDBList;
    }

    @Override
    public Boolean call() throws Exception {
        menuDBDao.newMenuDB(menuDBList);
        return true;
    }
}



//___________________________________картинки Callable_________________________________________


class CallableImgMenuFromDB implements Callable<List<ImgGazilla>>{

    private ImgGazillaDao imgGazillaDao;

    public CallableImgMenuFromDB(ImgGazillaDao imgGazillaDao) {
        this.imgGazillaDao = imgGazillaDao;
    }

    @Override
    public List<ImgGazilla> call() throws Exception {
        return imgGazillaDao.getOllImgFromBD();
    }
}

class CallableLoadImgOnDB implements Callable<Boolean>{

    private ImgGazillaDao imgGazillaDao;
    private List<ImgGazilla> list;

    public CallableLoadImgOnDB(ImgGazillaDao imgGazillaDao, List<ImgGazilla> list) {
        this.imgGazillaDao = imgGazillaDao;
        this.list = list;
    }

    @Override
    public Boolean call() throws Exception {
        imgGazillaDao.newImg(list);
        return true;
    }
}

class CallableLoadOneImg implements Callable<Boolean> {

    private ImgGazillaDao imgGazillaDao;
    private ImgGazilla imgGazilla;

    public CallableLoadOneImg(ImgGazillaDao imgGazillaDao, ImgGazilla imgGazilla) {
        this.imgGazillaDao = imgGazillaDao;
        this.imgGazilla = imgGazilla;
    }

    @Override
    public Boolean call() throws Exception {
        imgGazillaDao.newOneImg(imgGazilla);
        return true;
    }
}

class CallableClearTableImg implements Callable<Boolean>{

    private ImgGazillaDao imgGazillaDao;

    public CallableClearTableImg(ImgGazillaDao imgGazillaDao) {
        this.imgGazillaDao = imgGazillaDao;
    }

    @Override
    public Boolean call() throws Exception {
        imgGazillaDao.deleteOllImgTable();
        return true;
    }
}

class CallableImgById implements Callable<ImgGazilla>{

    private ImgGazillaDao imgGazillaDao;
    int id;

    public CallableImgById(ImgGazillaDao imgGazillaDao, int id) {
        this.imgGazillaDao = imgGazillaDao;
        this.id = id;
    }

    @Override
    public ImgGazilla call() throws Exception {
        return imgGazillaDao.getImgFromDbById(id);
    }
}
//__________________________________-промо Callable____________________________________________


class CallablePromoDB  implements Callable<List<PromoItem>> {

    private PromoDao promoDao;

    CallablePromoDB(PromoDao promoDao) {
        this.promoDao = promoDao;
    }


    @Override
    public List<PromoItem> call() throws Exception {
        return promoDao.getPromoDB();
    }


}


class AddNewPromo implements Callable<Boolean>{

    private PromoDao promoDao;
    private PromoItem promoItem;

    public AddNewPromo(PromoDao promoDao, PromoItem promoItem) {
        this.promoDao = promoDao;
        this.promoItem = promoItem;
    }

    @Override
    public Boolean call() throws Exception {
        promoDao.addNewPromoDB(promoItem);
        return true;
    }


}

class CallableUpdatePromoDB  implements Callable<Boolean> {

    private PromoDao promoDao;
    private List<PromoItem> list;

    CallableUpdatePromoDB(PromoDao promoDao, List<PromoItem> list) {
        this.promoDao = promoDao;
        this.list = list;
    }


    @Override
    public Boolean call() throws Exception {
        promoDao.loadOllOllPromo(list);
        return true;
    }
}

class CallableClearPromoTable implements Callable<Boolean>{

    private PromoDao promoDao;

    public CallableClearPromoTable(PromoDao promoDao) {
        this.promoDao = promoDao;
    }

    @Override
    public Boolean call() throws Exception {
        promoDao.deleteOllTablePromo();
        return true;
    }
}
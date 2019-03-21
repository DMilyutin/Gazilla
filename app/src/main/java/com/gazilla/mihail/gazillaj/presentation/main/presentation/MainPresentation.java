package com.gazilla.mihail.gazillaj.presentation.main.presentation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.utils.POJO.User;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.StaticCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;

import java.io.IOException;

import okhttp3.ResponseBody;

public class MainPresentation extends MvpPresenter<MainView> {

    private MainView mainView;
    private Context context;
    private SharedPref sharedPref;
    private InitializationAp initializationAp;

    public MainPresentation(MainView mainView, Context context) {
        initializationAp = InitializationAp.getInstance();
        this.mainView = mainView;
        this.context = context;
        sharedPref = new SharedPref(context);
        alertNotificationORTips();
    }

    public void updateUserInfo(){

        if (initializationAp.getRepositoryApi()!=null){
            Log.i("Loog", "Запрос на сервер User а");
            initializationAp.getRepositoryApi().userData(initializationAp.getUserWithKeys().getPublickey(),
                    initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), ""),
                    new UserCallBack() {
                @Override
                public void userCallBack(User user) {
                    setUserwithUserKey(user);
                }

                @Override
                public void errorUser(String error) {
                    new BugReport().sendBugInfo(error, "MainPresentation.updateUserInfo.errorUser");
                }
            }, new FailCallBack() {
                @Override
                public void setError(Throwable throwable) {
                    new BugReport().sendBugInfo(throwable.getMessage(), "MainPresentation.updateUserInfo.setError.Throwable");
                }
            });
        }
        else
            mainView.showErrorDialog("Ошибка инициализации, перезапустите пожалуйста приложение");

    }

    private void setUserwithUserKey(User user){
        if (initializationAp.getUserWithKeys()!=null){
            initializationAp.getUserWithKeys().setId(user.getId());
            initializationAp.getUserWithKeys().setLevel(user.getLevel());
            initializationAp.getUserWithKeys().setRefererLink(user.getRefererLink());
            initializationAp.getUserWithKeys().setName(user.getName());
            initializationAp.getUserWithKeys().setPhone(user.getPhone());
            initializationAp.getUserWithKeys().setScore(user.getScore());
            initializationAp.getUserWithKeys().setSum(user.getSum());
            initializationAp.getUserWithKeys().setFavorites(user.getFavorites());

            Log.i("Loog", "update User getScore" + user.getScore());
            Log.i("Loog", "update User getSum" + user.getSum());
        }
        else
            initializationAp.setUserWithKeys(new UserWithKeys(user.getId(), user.getName()
                    , user.getPhone(), user.getEmail(), user.getSum(), user.getScore(), user.getLevel(),
                    sharedPref.getPublickKeyFromPref(), sharedPref.getPrivateKeyFromPref(), user.getRefererLink(),
                    user.getFavorites()));

        mainView.updateInfo(user.getScore());
    }

    private void alertNotificationORTips(){
        //if (!sharedPref.getFirstStart()){
        if (true){
            if (initializationAp.getNotificaton()!=null){
                getImgItem(initializationAp.getNotificaton().getId());
            }

        }
    }

    private void getImgItem(int id){
        initializationAp.getRepositoryApi().getStaticFromServer("alerts", String.valueOf(id),
                new StaticCallBack() {
                    @Override
                    public void myStatic(ResponseBody responseBody) throws IOException {
                        if (responseBody!=null){
                            byte[] b = responseBody.bytes();
                            Log.i("Loog", "Загрузка завершена");
                            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                            new AppDialogs().dialogNotification(context, initializationAp.getNotificaton(), mainView, bitmap);
                        }

                        else  Log.i("Loog", "Загрузка пусто");
                    }

                    @Override
                    public void showError(String error) {
                        Log.i("Loog", "Загрузка showError " + error);
                        //presentView.setImgItem(null);
                        //presentView.errorDialog("Фото времененно недоступно");
                         //new BugReport().sendBugInfo(error, "Ошибка загрузки картинки для алерта пуша");
                        new AppDialogs().dialogNotification(context, initializationAp.getNotificaton(), mainView, null);
                    }
                }, new FailCallBack() {
                    @Override
                    public void setError(Throwable throwable) {
                        new BugReport().sendBugInfo(throwable.getMessage(), "MainPresentation.getImgItem.setError.Throwable");
                    }
                });
    }

    public void sendAnswerNotification(int alertId, int commandId){
        if (initializationAp.getRepositoryApi()==null) return;

        String dat = "alertId=" + alertId
                +"&"+ "commandId=" + commandId;

        String publicKey = initializationAp.getUserWithKeys().getPublickey();
        String signature = initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), dat);
        initializationAp.getRepositoryApi().sendAnswerUserAboutNotification(publicKey, alertId, commandId, signature,
                new SuccessCallBack() {
                    @Override
                    public void reservResponse(Success success) {
                        Log.i("Loog", "sendAnswerNotification - " + success.isSuccess());
                    }

                    @Override
                    public void errorResponse(String error) {
                        Log.i("Loog", "sendAnswerNotification error - " + error);
                        new BugReport().sendBugInfo(error, "MainPresentation.sendAnswerNotification.errorResponse");
                    }
                }, new FailCallBack() {
                    @Override
                    public void setError(Throwable throwable) {
                        Log.i("Loog", "sendAnswerNotification Throwable - " + throwable.getMessage());
                        new BugReport().sendBugInfo(throwable.getMessage(), "MainPresentation.sendAnswerNotification.Throwable");
                    }
                });

    }

    public void saveVersionNotification(){
        sharedPref.saveVersionNotification(initializationAp.getLatestVersion().getDate());
    }

}

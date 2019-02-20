package com.gazilla.mihail.gazillaj.presentation.main.presentation;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.utils.POJO.User;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;

public class MainPresentation {

    private MainView mainView;
    private Context context;
    private SharedPref sharedPref;

    public MainPresentation(MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
        sharedPref = new SharedPref(context);
        alertNotificationORTips();
    }

    public void updateUserInfo(){

        if (Initialization.repositoryApi!=null){
            Log.i("Loog", "Запрос на сервер User а");
            Initialization.repositoryApi.userData(new UserCallBack() {
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
        if (Initialization.userWithKeys!=null){
            Initialization.userWithKeys.setId(user.getId());
            Initialization.userWithKeys.setLevel(user.getLevel());
            Initialization.userWithKeys.setRefererLink(user.getRefererLink());
            Initialization.userWithKeys.setName(user.getName());
            Initialization.userWithKeys.setPhone(user.getPhone());
            Initialization.userWithKeys.setScore(user.getScore());
            Initialization.userWithKeys.setSum(user.getSum());
            Initialization.userWithKeys.setFavorites(user.getFavorites());

            Log.i("Loog", "update User getScore" + user.getScore());
            Log.i("Loog", "update User getSum" + user.getSum());
        }
        else
            Initialization.userWithKeys = new UserWithKeys(user.getId(), user.getName()
                    , user.getPhone(), user.getEmail(), user.getSum(), user.getScore(), user.getLevel(),
                    sharedPref.getPublickKeyFromPref(), sharedPref.getPrivateKeyFromPref(), user.getRefererLink(),
                    user.getFavorites());

        mainView.updateInfo(user.getScore());
    }

    private void alertNotificationORTips(){
        if (!sharedPref.getFirstStart()){
            if (Initialization.notificaton!=null)
                new AppDialogs().dialogNotification(context, Initialization.notificaton, mainView);
        }
    }

    public void sendAnswerNotification(int alertId, int commandId){
        if (Initialization.repositoryApi==null) return;

        String dat = "alertId=" + alertId
                +"&"+ "commandId=" + commandId;

        String publicKey = Initialization.userWithKeys.getPublickey();
        String signature = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), dat);
        Initialization.repositoryApi.sendAnswerUserAboutNotification(publicKey, alertId, commandId, signature,
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


}

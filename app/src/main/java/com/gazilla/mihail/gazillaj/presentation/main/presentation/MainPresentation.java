package com.gazilla.mihail.gazillaj.presentation.main.presentation;

import android.util.Log;

import com.gazilla.mihail.gazillaj.POJO.User;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;

public class MainPresentation {

    private MainView mainView;

    public MainPresentation(MainView mainView) {
        this.mainView = mainView;
    }

    public void updateUserInfo(){
        Initialization.repositoryApi.userData(new UserCallBack() {
            @Override
            public void userCallBack(User user) {
                setUserwithUserKey(user);
            }

            @Override
            public void errorUser(String error) {

            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {

            }
        });

    }

    private void setUserwithUserKey(User user){
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
        mainView.updateInfo(Initialization.userWithKeys.getScore());
    }
}

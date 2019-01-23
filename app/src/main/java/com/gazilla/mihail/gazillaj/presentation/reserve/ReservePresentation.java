package com.gazilla.mihail.gazillaj.presentation.reserve;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.utils.POJO.Reserve;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.utils.POJO.User;
import com.gazilla.mihail.gazillaj.model.interactor.ReserveInteractor;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;

import java.util.Calendar;


public class ReservePresentation {

    private SharedPref sharedPref;

    private ReserveView reserveView;
    private ReserveInteractor reserveInteractor;

    public ReservePresentation(ReserveView reserveView, ReserveInteractor reserveInteractor, Context context) {
        this.reserveView = reserveView;
        this.reserveInteractor = reserveInteractor;
        sharedPref = new SharedPref(context);
    }

    public void reservingPresenter(Reserve reserve, Boolean preorder){

        String dat = "comment="+reserve.getCommentL()+"&"+
                    "date="+reserve.getDate()+"&"+
                    "hours="+reserve.getHours()+"&"+
                    "name="+reserve.getName()+"&"+
                    "phone="+reserve.getPhone()+"&"+
                    "preorder="+preorder+"&"+
                    "qty="+reserve.getQty();

        String signatur = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(),  dat);

        reserve.setDate(zamenaPlusa(reserve.getDate()));
        reserve.setPhone(zamenaPlusa(reserve.getPhone()));

        reserveInteractor.setReserve(reserve.getQty(), reserve.getHours(), reserve.getDate(),
                reserve.getName(), reserve.getPhone(), reserve.getCommentL(), preorder,  signatur,
                new SuccessCallBack() {
                    @Override
                    public void reservResponse(Success success) {
                        if (success.isSuccess()){
                        String mess = "Стол успешно забронирован! \n Ждем Вас в назначенное время";
                        reserveView.resultReserve(mess);
                        }
                        else {

                            Log.i("Loog", "success mes " + success.getMessage());
                            String mess = "Произошло недорозуменее :( \n Позвоните нам пожалуйста";
                            reserveView.resultReserve(mess);
                        }

                        Log.i("Loog", "Reserve - " + success.isSuccess());

                    }

                    @Override
                    public void errorResponse(String error) {
                        if (error.contains("qty required")){
                            reserveView.showErrorr("Ошибка кол-ва гостей");
                        }
                        else if (error.contains("hours required")){
                            reserveView.showErrorr("Ошибка кол-ва часов");
                        }
                        else if (error.contains("invalid date")){
                            reserveView.showErrorr("Ошибка выбранной даты");
                        }
                        else if (error.contains("invalid preorder")){
                            reserveView.showErrorr("Ошибка предзаказа");
                        }
                        else if (error.contains("invalid reserve time")){
                            reserveView.showErrorr("Ошибка времени брони");
                        }
                        else if (error.contains("name and phone required")){
                            reserveView.showErrorr("Ошибка данных пользователя");
                        }

                        reserveView.resultReserve("Ошибка \n" + error);
                        Log.i("Loog", "errorReserve - " + error);
                    }
                }, new FailCallBack() {
                    @Override
                    public void setError(Throwable throwable) {
                        reserveView.resultReserve("Ошибка \n" + throwable);
                        Log.i("Loog", "errorReserveT - " + throwable.getMessage());
                    }
                });
    }

    private Boolean checkDateAndTime(Reserve reserve) {
        //Todo

        Calendar thisDataAndTime = Calendar.getInstance();

        Calendar userTime;
        Calendar userDate;

        return false;
    }

    private String zamenaPlusa(String dat) {

        return dat.replace("+", "%2B");
    }

    public void checkUserInfoOnDB(){
        String name = sharedPref.getNameFromPref();
        String phone = sharedPref.getPhoneFromPref();
        reserveView.inputUserInfo(name, phone);
    }

    public void checkUserInfo(){
        Initialization.repositoryApi.userData(new UserCallBack() {
            @Override
            public void userCallBack(User user) {
                if(user.getName().equals("")||
                   user.getEmail().equals("")||
                   user.getPhone().equals("")){
                    reserveView.unRegUser();
                }
                else
                    reserveView.inputUserInfo(user.getName(), user.getPhone());
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

}

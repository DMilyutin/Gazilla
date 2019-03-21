package com.gazilla.mihail.gazillaj.presentation.reserve;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.POJO.Reserve;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.model.interactor.ReserveInteractor;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;


public class ReservePresentation extends MvpPresenter<ReserveView> {

    private SharedPref sharedPref;

    private ReserveView reserveView;
    private ReserveInteractor reserveInteractor;
    private InitializationAp initializationAp = InitializationAp.getInstance();

    public ReservePresentation(ReserveView reserveView, ReserveInteractor reserveInteractor, Context context) {
        this.reserveView = reserveView;
        this.reserveInteractor = reserveInteractor;
        sharedPref = new SharedPref(context);
    }

    public void reservingPresenter(Reserve reserve, Boolean preorder){

        reserve.setPhone(checkFormatPhone(reserve.getPhone()));
        if (reserve.getPhone().equals("")) return;

        String dat = "comment="+reserve.getCommentL()+"&"+
                    "date="+reserve.getDate()+"&"+
                    "hours="+reserve.getHours()+"&"+
                    "name="+reserve.getName()+"&"+
                    "phone="+reserve.getPhone()+"&"+
                    "preorder="+preorder+"&"+
                    "qty="+reserve.getQty();

        String signatur = initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(),  dat);

        reserve.setDate(zamenaPlusa(reserve.getDate()));


        reserveInteractor.setReserve(reserve.getQty(), reserve.getHours(), reserve.getDate(),
                reserve.getName(), reserve.getPhone(), reserve.getCommentL(), preorder,  signatur,
                new SuccessCallBack() {
                    @Override
                    public void reservResponse(Success success) {
                        reserveView.clouseAppDialog();
                        if (success.isSuccess()){
                        String mess = "Стол успешно забронирован!\n\nЖдем Вас в назначенное время";
                        reserveView.resultReserve(mess);
                        }
                        else {
                          new BugReport().sendBugInfo(success.getMessage(), "ReservePresentation.reservingPresenter.reservResponse");
                            Log.i("Loog", "success mes " + success.getMessage());
                            String mess = "Произошло недорозуменее :( \n Позвоните нам пожалуйста";
                            reserveView.resultReserve(mess);
                        }

                        Log.i("Loog", "Reserve - " + success.isSuccess());

                    }

                    @Override
                    public void errorResponse(String error) {
                        reserveView.clouseAppDialog();
                        if (error.contains("qty required")){
                            reserveView.showWorningDialog("Ошибка кол-ва гостей");
                        }
                        else if (error.contains("hours required")){
                            reserveView.showWorningDialog("Ошибка кол-ва часов");
                        }
                        else if (error.contains("invalid date")){
                            reserveView.showWorningDialog("Ошибка выбранной даты");
                        }
                        else if (error.contains("invalid preorder")){
                            reserveView.showWorningDialog("Ошибка предзаказа");
                        }
                        else if (error.contains("invalid reserve time")){
                            reserveView.showWorningDialog("Ошибка времени брони");
                        }
                        else if (error.contains("name and phone required")){
                            reserveView.showWorningDialog("Ошибка данных пользователя");
                        }
                        else {
                            new BugReport().sendBugInfo(error, "ReservePresentation.reservingPresenter.errorResponse");
                            reserveView.showWorningDialog("Ошибка \n" + error);

                        }
                        Log.i("Loog", "errorReserve - " + error);
                    }
                }, new FailCallBack() {
                    @Override
                    public void setError(Throwable throwable) {
                        new BugReport().sendBugInfo(throwable.getMessage(), "ReservePresentation.reservingPresenter.setError.Throwable");
                        reserveView.clouseAppDialog();
                        reserveView.showWorningDialog("К сожалению, нам не удалось забронировать столик в автоматическом режиме. В ближайшее время с вами свяжется Администратор, чтобы забронировать Вам место по телефону");
                        Log.i("Loog", "errorReserveT - " + throwable.getMessage());
                    }
                });
    }

    private String checkFormatPhone(String s) {
        if (s==null||s.equals("")) return ""; // пусте поле

        if(s.charAt(0)=='8'&&s.length()==11){ // 8 ххх ххх хх хх
            return ""+ s.charAt(1)+s.charAt(2)+s.charAt(3)+s.charAt(4)+s.charAt(5)+s.charAt(6)+s.charAt(7)+s.charAt(8)+s.charAt(9)+s.charAt(10);
        }
        else if (s.charAt(0)=='+'&&s.charAt(1)=='7'&&s.length()==12) // +7 ххх ххх хх хх
            return ""+ s.charAt(2)+s.charAt(3)+s.charAt(4)+s.charAt(5)+s.charAt(6)+s.charAt(7)+s.charAt(8)+s.charAt(9)+s.charAt(10)+s.charAt(11);
        else if (s.charAt(0)=='9'&&s.length()==10) // 9хх ххх хх хх
            return s;
        else {
            reserveView.clouseAppDialog();
            reserveView.showWorningDialog("Неверный формат номера");
            return "";
        }
    }

    private String zamenaPlusa(String dat) {
        return dat.replace("+", "%2B");
    }


    public void checkUserInfo(){
        reserveView.inputUserInfo(sharedPref.getNameFromPref(), sharedPref.getPhoneFromPref());
    }

}

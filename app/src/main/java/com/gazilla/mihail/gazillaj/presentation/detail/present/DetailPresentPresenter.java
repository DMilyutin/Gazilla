package com.gazilla.mihail.gazillaj.presentation.detail.present;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.StaticCallBack;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.ResponseBody;

@InjectViewState
public class DetailPresentPresenter extends MvpPresenter<DetailPresentView> {


    public void pressBtNext(){
        getViewState().openFirstDialog();
    }

    public void byPresent(){
        getViewState().openSecondDialog();
    }

    public  void cloceDetailPresent(){
        getViewState().acsessBuy();
    }

    public void getImgItem(String id){
        InitializationAp.getInstance().getRepositoryApi().getStaticFromServer("menu", id,
                new StaticCallBack() {
                    @Override
                    public void myStatic(ResponseBody responseBody) throws IOException {

                        if (responseBody!=null){
                            byte[] b = responseBody.bytes();
                            Log.i("Loog", "Загрузка завершена");
                        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                            getViewState().setImgItem(bitmap);

                        }

                        else  Log.i("Loog", "Загрузка пусто");
                    }

                    @Override
                    public void showError(String error) {
                        Log.i("Loog", "Загрузка showError " + error);
                        getViewState().setImgItem(null);
                        getViewState().errorDialog("Фото времененно недоступно");
                       // new BugReport().sendBugInfo(error, "DetailPresentPresenter.getImgItem.showError");
                    }
                }, new FailCallBack() {
                    @Override
                    public void setError(Throwable throwable) {
                        new BugReport().sendBugInfo(throwable.getMessage(), "DetailPresentPresenter.getImgItem.setError.Throwable");
                    }
                });
    }

    private void checkAcsessByWin(){
        Calendar date = Calendar.getInstance();
        if (date.get(Calendar.DAY_OF_WEEK) == 4){

        }
    }


}

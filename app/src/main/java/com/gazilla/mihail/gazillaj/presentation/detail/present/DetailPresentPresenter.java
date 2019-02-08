package com.gazilla.mihail.gazillaj.presentation.detail.present;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.StaticCallBack;

import java.io.IOException;

import okhttp3.ResponseBody;

public class DetailPresentPresenter {



    private DetailPresentView presentView;

    public DetailPresentPresenter(DetailPresentView presentView) {
        this.presentView = presentView;
    }

    public void pressBtNext(){
        presentView.openFirstDialog();
    }

    public void byPresent(){
        presentView.openSecondDialog();
    }

    public  void cloceDetailPresent(){
        presentView.acsessBuy();
    }

    public void getImgItem(String id){
        Initialization.repositoryApi.getStaticFromServer("menu", id,
                new StaticCallBack() {
                    @Override
                    public void myStatic(ResponseBody responseBody) throws IOException {

                        if (responseBody!=null){
                            byte[] b = responseBody.bytes();
                            Log.i("Loog", "Загрузка завершена");
                        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                        presentView.setImgItem(bitmap);

                        }

                        else  Log.i("Loog", "Загрузка пусто");
                    }

                    @Override
                    public void showError(String error) {
                        Log.i("Loog", "Загрузка showError " + error);
                        presentView.setImgItem(null);
                        presentView.errorDialog("Фото времененно недоступно");
                       // new BugReport().sendBugInfo(error, "DetailPresentPresenter.getImgItem.showError");
                    }
                }, new FailCallBack() {
                    @Override
                    public void setError(Throwable throwable) {
                        new BugReport().sendBugInfo(throwable.getMessage(), "DetailPresentPresenter.getImgItem.setError.Throwable");
                    }
                });
    }


}

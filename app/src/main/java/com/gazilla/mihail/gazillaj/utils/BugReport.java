package com.gazilla.mihail.gazillaj.utils;

import android.util.Log;

import com.gazilla.mihail.gazillaj.BuildConfig;
import com.gazilla.mihail.gazillaj.model.interactor.bugReport.BugReportInterator;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class BugReport {

    private BugReportInterator bugReportInterator;

    public BugReport() {
        this.bugReportInterator = new BugReportInterator();
    }

    public void sendBugInfo(String ex, String location){

        String mes = createErrorMesage(ex, location);
        Log.i("Loog", "!!!!!!!!!!!Отправка отчета об ошибке!!!!!!!!!!!!!!");
        bugReportInterator.newBug(mes, new SuccessCallBack() {
            @Override
            public void reservResponse(Success success) {
                boolean b = success.isSuccess();
                if (b)
                    Log.i("Loog", "Отправка отчета об ошибке -Четко");
                else
                    Log.i("Loog", success.getMessage());
            }

            @Override
            public void errorResponse(String error) {
                Log.i("Loog", "Отправк отчета об ошибке - "+ error);
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "Отправк отчета об ошибке Throwable"+ throwable.getMessage());
            }
        });
    }

    private String createErrorMesage(String ex, String location){
        int versionCode = BuildConfig.VERSION_CODE;
        String mes ="Версия программы - "+ String.valueOf(versionCode) + ". Android bug: ";

        if (ex!=null&&!ex.equals(""))
            mes+=ex;
        mes+="\n"+location;

        return mes;
    }
}

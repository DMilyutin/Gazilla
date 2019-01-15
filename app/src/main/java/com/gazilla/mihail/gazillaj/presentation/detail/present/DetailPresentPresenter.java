package com.gazilla.mihail.gazillaj.presentation.detail.present;

import android.util.Log;

import com.gazilla.mihail.gazillaj.POJO.Success;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

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




}

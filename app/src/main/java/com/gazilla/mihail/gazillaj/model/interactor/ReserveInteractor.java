package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class ReserveInteractor {

    public void setReserve(int qty, int hours, String date,
                           String name, String phone, String commentL, Boolean preorder,
                           String signatur, SuccessCallBack successCallBack, FailCallBack failCallBack){

        Initialization.repositoryApi.reserving(qty, hours, date, phone, name, commentL, preorder,
                Initialization.userWithKeys.getPublickey(),
                signatur, successCallBack, failCallBack);
    }


}

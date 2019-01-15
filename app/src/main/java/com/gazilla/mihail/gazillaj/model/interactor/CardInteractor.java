package com.gazilla.mihail.gazillaj.model.interactor;

import android.content.Context;

import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LevelsCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.QTYCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.WheelCallBack;

public class CardInteractor {

    private SharedPref sharedPref;
    private Context context;

    public CardInteractor( Context context) {
        this.sharedPref = new SharedPref(context);
        this.context = context;
    }

    public String getMyId() {
         return String.valueOf(sharedPref.getIdFromPref());
    }

    public void mySpins(QTYCallBack qtyCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.mySpins(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""),
                qtyCallBack, failCallBack);
    }

    public void wheeling(WheelCallBack wheelCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.wheeling(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""),
                wheelCallBack, failCallBack);

    }

    public void level(LevelsCallBack levelsCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.levels(levelsCallBack, failCallBack);
    }
}

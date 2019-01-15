package com.gazilla.mihail.gazillaj.presentation.Initialization;

import android.content.Context;

import com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor.InitInteractor;


public class InitPresentation {

    private InitInteractor initInteractor;


    public InitPresentation(InnitView innitView, Context context) {

        initInteractor = new InitInteractor(context, innitView);
    }

    public void startInitialization(){
       initInteractor.startInit();
    }

    public void registerNext(){
        initInteractor.startInit();
    }


}



package com.gazilla.mihail.gazillaj.presentation.detail.present;

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

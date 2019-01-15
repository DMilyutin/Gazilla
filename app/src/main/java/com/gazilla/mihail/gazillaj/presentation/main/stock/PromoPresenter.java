package com.gazilla.mihail.gazillaj.presentation.main.stock;

import android.util.Log;

import com.gazilla.mihail.gazillaj.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.model.interactor.PromoInteractor;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PromoCallBack;

import java.util.ArrayList;
import java.util.List;

public class PromoPresenter {

    private PromoView promoView;
    private PromoInteractor promoInteractor;

    public PromoPresenter(PromoView promoView, PromoInteractor promoInteractor) {
        this.promoView = promoView;
        this.promoInteractor = promoInteractor;
    }

    public void myPromo(){
        promoInteractor.promoFromDB(new PromoCallBack() {
            @Override
            public void myPromo(List<PromoItem> promoItemList) {
                promoView.setPromoAdapter(promoItemList);
            }

            @Override
            public void showError(String error) {

            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {

            }
        });
    }

    /*private List<PromoItem> defPromo(){
        List<PromoItem> promoItemList = new ArrayList<>();

        PromoItem
    }*/

    /*public void myPromo(){
        promoInteractor.promoFromDB(new PromoCallBack() {
            @Override
            public void myPromo(List<PromoItem> promoItemList) {
                Log.i("Loog", "promo from DB -" + promoItemList.size());
                promoView.setPromoAdapter(promoItemList);

            }

            @Override
            public void showError(String error) {
                Log.i("Loog", "Error promo -" + error);
            }
        });
    }*/
}

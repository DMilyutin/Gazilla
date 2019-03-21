package com.gazilla.mihail.gazillaj.presentation.main.stock;

import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.model.interactor.PromoInteractor;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PromoCallBack;

import java.util.List;

public class PromoPresenter extends MvpPresenter<PromoView> {

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
                //promoView.setPromoAdapter(promoItemList);
                for (PromoItem item : promoItemList) {
                    Log.i("Loog", "Промо название - "+ item.getName());
                    Log.i("Loog", "Промо тип - "+ item.getPromoType());
                    Log.i("Loog", "Промо описание - "+ item.getDescription());
                    Log.i("Loog", "Промо id - "+ item.getId());
                    Log.i("Loog", "");
                }
            }

            @Override
            public void showError(String error) {
                Log.i("Loog", "Промо showError - "+ error);
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "Промо setError - "+ throwable.getMessage());
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

package com.gazilla.mihail.gazillaj.presentation.main.stock;

import com.arellomobile.mvp.MvpView;
import com.gazilla.mihail.gazillaj.utils.POJO.PromoItem;

import java.util.List;

public interface PromoView extends MvpView {

    void setPromoAdapter(List<PromoItem> promoItems);
    void showErrorCode(int error);
}

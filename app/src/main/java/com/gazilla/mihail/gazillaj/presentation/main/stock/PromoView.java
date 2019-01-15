package com.gazilla.mihail.gazillaj.presentation.main.stock;

import com.gazilla.mihail.gazillaj.POJO.PromoItem;

import java.util.List;

public interface PromoView {

    void setPromoAdapter(List<PromoItem> promoItems);
    void showErrorCode(int error);
}

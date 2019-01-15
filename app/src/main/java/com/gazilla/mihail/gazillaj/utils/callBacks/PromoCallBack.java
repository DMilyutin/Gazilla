package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.POJO.PromoItem;

import java.util.List;

public interface PromoCallBack {
    void myPromo(List<PromoItem> promoItemList);
    void showError(String error);
}

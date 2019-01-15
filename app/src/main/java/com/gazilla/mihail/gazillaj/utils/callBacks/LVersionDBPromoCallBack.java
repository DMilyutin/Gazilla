package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.POJO.LatestVersion;

public interface LVersionDBPromoCallBack {

    void versionDBPromo(LatestVersion latestVersion);
    void showError(int error);
}

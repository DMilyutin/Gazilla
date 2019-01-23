package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;

public interface LVersionDBPromoCallBack {

    void versionDBPromo(LatestVersion latestVersion);
    void showError(int error);
}

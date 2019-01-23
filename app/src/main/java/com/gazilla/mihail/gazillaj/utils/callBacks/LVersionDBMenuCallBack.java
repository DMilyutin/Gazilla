package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;

public interface LVersionDBMenuCallBack {
    void versionDBMenu(LatestVersion latestVersion);
    void showError(int error);
}

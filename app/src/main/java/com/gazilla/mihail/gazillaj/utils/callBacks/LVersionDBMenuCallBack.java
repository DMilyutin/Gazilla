package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.POJO.LatestVersion;

public interface LVersionDBMenuCallBack {
    boolean versionDBMenu(LatestVersion latestVersion);
    void showError(int error);
}

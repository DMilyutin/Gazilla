package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;

public interface LVersionDBMenuItemCallBack {
    void versionDBMenuItem(LatestVersion latestVersion);
    void showError(int error);
}

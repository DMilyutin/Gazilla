package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;

import io.reactivex.Observable;

public interface LVersionDBMenuCallBack {
    void versionDBMenu(LatestVersion latestVersion);
    void showError(int error);
}

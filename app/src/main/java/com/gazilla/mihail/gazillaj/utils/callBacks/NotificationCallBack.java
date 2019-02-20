package com.gazilla.mihail.gazillaj.utils.callBacks;



import com.gazilla.mihail.gazillaj.utils.POJO.Notificaton;

import java.util.List;

public interface NotificationCallBack {

    void ollNotification(List<Notificaton> notificationList);
    void showNotificationError(String error);
}

package com.gazilla.mihail.gazillaj.model.interactor.bugReport;

import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class BugReportInterator {

    public void newBug(String mess, SuccessCallBack successCallBack, FailCallBack failCallBack){
        String publicKey = Initialization.userWithKeys.getPublickey();
        String dat = "message="+mess;
        String signatur = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(),  dat);

        Initialization.repositoryApi.sendBugReport(mess, publicKey,signatur, successCallBack, failCallBack);
    }

}

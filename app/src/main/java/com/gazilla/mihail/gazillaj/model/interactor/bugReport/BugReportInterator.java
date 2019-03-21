package com.gazilla.mihail.gazillaj.model.interactor.bugReport;

import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class BugReportInterator {

    public void newBug(String mess, SuccessCallBack successCallBack, FailCallBack failCallBack){
        InitializationAp initializationAp = InitializationAp.getInstance();

        String publicKey = initializationAp.getUserWithKeys().getPublickey();
        String dat = "message="+mess;
        String signatur = initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(),  dat);

        initializationAp.getRepositoryApi().sendBugReport(mess, publicKey,signatur, successCallBack, failCallBack);
    }

}

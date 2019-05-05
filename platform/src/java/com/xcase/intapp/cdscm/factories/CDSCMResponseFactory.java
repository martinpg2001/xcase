package com.xcase.intapp.cdscm.factories;

import com.xcase.intapp.cdscm.transputs.CreateClientResponse;
import com.xcase.intapp.cdscm.transputs.DeleteClientResponse;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityResponse;
import com.xcase.intapp.cdscm.transputs.PutClientSecurityResponse;

public class CDSCMResponseFactory extends BaseCDSCMFactory {

    public static GetClientSecurityResponse createGetClientSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetClientSecurityResponse");
        return (GetClientSecurityResponse) obj;
    }

    public static CreateClientResponse createCreateClientResponse() {
        // TODO Auto-generated method stub
        return null;
    }

    public static DeleteClientResponse createDeleteClientResponse() {
        // TODO Auto-generated method stub
        return null;
    }

    public static PutClientSecurityResponse createPutClientSecurityResponse() {
        // TODO Auto-generated method stub
        return null;
    }

}

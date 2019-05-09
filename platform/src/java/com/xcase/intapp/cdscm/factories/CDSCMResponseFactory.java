package com.xcase.intapp.cdscm.factories;

import com.xcase.intapp.cdscm.transputs.CreateClientResponse;
import com.xcase.intapp.cdscm.transputs.DeleteClientResponse;
import com.xcase.intapp.cdscm.transputs.GetClientResponse;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityResponse;
import com.xcase.intapp.cdscm.transputs.GetClientsModifiedSinceDateResponse;
import com.xcase.intapp.cdscm.transputs.PutClientSecurityResponse;

public class CDSCMResponseFactory extends BaseCDSCMFactory {

    public static CreateClientResponse createCreateClientResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.CreateClientResponse");
        return (CreateClientResponse) obj;
    }

    public static DeleteClientResponse createDeleteClientResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.DeleteClientResponse");
        return (DeleteClientResponse) obj;
    }
    
    public static GetClientResponse createGetClientResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetClientResponse");
        return (GetClientResponse) obj;
    }
    
    public static GetClientSecurityResponse createGetClientSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetClientSecurityResponse");
        return (GetClientSecurityResponse) obj;
    }

    public static PutClientSecurityResponse createPutClientSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.PutClientSecurityResponse");
        return (PutClientSecurityResponse) obj;
    }

    public static GetClientsModifiedSinceDateResponse createGetClientsModifiedSinceDateResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetClientsModifiedSinceDateResponse");
        return (GetClientsModifiedSinceDateResponse) obj;
    }

}

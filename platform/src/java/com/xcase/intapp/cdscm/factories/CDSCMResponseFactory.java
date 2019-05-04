package com.xcase.intapp.cdscm.factories;

import com.xcase.intapp.cdscm.transputs.GetClientSecurityResponse;

public class CDSCMResponseFactory extends BaseCDSCMFactory {

    public static GetClientSecurityResponse createGetClientSecurityResponse() {
        Object obj = newInstanceOf("cdscm.config.responsefactory.GetClientSecurityResponse");
        return (GetClientSecurityResponse) obj;
    }

}

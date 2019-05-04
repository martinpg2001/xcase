package com.xcase.intapp.cdsusers.factories;

import com.xcase.intapp.cdsusers.transputs.GetClientSecurityResponse;

public class CDSUsersResponseFactory extends BaseCDSUsersFactory {

    public static GetClientSecurityResponse createGetClientSecurityResponse() {
        Object obj = newInstanceOf("cdsusers.config.responsefactory.GetClientSecurityResponse");
        return (GetClientSecurityResponse) obj;
    }

}

package com.xcase.intapp.cdsusers;

import com.xcase.intapp.cdsusers.transputs.GetClientSecurityRequest;
import com.xcase.intapp.cdsusers.transputs.GetClientSecurityResponse;

public interface CDSUsersExternalAPI {

    GetClientSecurityResponse getClientSecurity(GetClientSecurityRequest getClientSecurityRequest);

}

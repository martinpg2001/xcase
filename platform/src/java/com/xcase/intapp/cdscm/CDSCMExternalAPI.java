package com.xcase.intapp.cdscm;

import com.xcase.intapp.cdscm.transputs.GetClientSecurityRequest;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityResponse;

public interface CDSCMExternalAPI {

    GetClientSecurityResponse getClientSecurity(GetClientSecurityRequest getClientSecurityRequest);

}

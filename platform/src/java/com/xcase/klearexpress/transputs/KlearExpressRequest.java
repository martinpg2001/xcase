package com.xcase.klearexpress.transputs;

import com.xcase.common.transputs.CommonRequest;

public interface KlearExpressRequest extends CommonRequest {
    String getAccessToken();
    void setAccessToken(String accessToken);
}

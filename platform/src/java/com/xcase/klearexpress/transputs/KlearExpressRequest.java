package com.xcase.klearexpress.transputs;

import com.xcase.common.transputs.CommonRequest;

public interface KlearExpressRequest extends CommonRequest {
    String getAccessToken();
    String getAPIUrl();
    void setAccessToken(String accessToken);
    void setAPIUrl(String apiUrl);
}

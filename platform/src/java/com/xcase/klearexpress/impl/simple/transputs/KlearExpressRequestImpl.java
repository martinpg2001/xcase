package com.xcase.klearexpress.impl.simple.transputs;

import com.xcase.common.impl.simple.transputs.*;
import com.xcase.klearexpress.transputs.KlearExpressRequest;

public class KlearExpressRequestImpl extends CommonRequestImpl implements KlearExpressRequest {
    private String accessToken;
    private String apiUrl;
    
    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getAPIUrl() {
        return apiUrl;
    }

    @Override
    public void setAPIUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

}

package com.xcase.klearexpress.impl.simple.transputs;

import com.xcase.common.impl.simple.transputs.*;
import com.xcase.klearexpress.transputs.KlearExpressRequest;

public class KlearExpressRequestImpl extends CommonRequestImpl implements KlearExpressRequest {
    private String accessToken;
    
    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}

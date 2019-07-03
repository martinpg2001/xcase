package com.xcase.klearexpress.impl.simple.transputs;

import com.xcase.common.impl.simple.transputs.*;
import com.xcase.klearexpress.transputs.KlearExpressResponse;

public class KlearExpressResponseImpl extends CommonResponseImpl implements KlearExpressResponse {
    private String message;
    private int responseCode;
    
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

}

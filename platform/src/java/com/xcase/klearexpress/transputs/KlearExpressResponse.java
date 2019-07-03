package com.xcase.klearexpress.transputs;

import com.xcase.common.transputs.*;

public interface KlearExpressResponse extends CommonResponse {
    int getResponseCode();
    void setResponseCode(int responseCode);
}

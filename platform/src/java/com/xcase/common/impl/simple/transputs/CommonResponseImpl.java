/**
 * Copyright 2018 Xcase All rights reserved.
 */
package com.xcase.common.impl.simple.transputs;

import com.xcase.common.transputs.CommonResponse;
import org.apache.http.StatusLine;

/**
 *
 * @author martinpg
 */
public class CommonResponseImpl implements CommonResponse {
    private String message = "SUCCESS";
    
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}

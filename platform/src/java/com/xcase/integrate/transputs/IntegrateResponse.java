/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.common.transputs.CommonResponse;

/**
 *
 * @author martin
 */
public interface IntegrateResponse extends CommonResponse {

    public String getMessage();

    public void setMessage(String message);

    public String getStatus();

    public void setStatus(String status);
}

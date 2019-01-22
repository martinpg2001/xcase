/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martin
 */
public interface GetLogRequest extends IntegrateRequest {

    public Integer getLogId();

    public String getLogType();

    public void setLogId(Integer logId);

    public void setLogType(String logType);
}

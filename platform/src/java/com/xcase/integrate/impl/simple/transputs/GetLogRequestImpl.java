/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.GetLogRequest;

/**
 *
 * @author martin
 */
public class GetLogRequestImpl extends IntegrateRequestImpl implements GetLogRequest {

    private Integer logId;
    private String logType;

    public Integer getLogId() {
        return this.logId;
    }

    public String getLogType() {
        return this.logType;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}

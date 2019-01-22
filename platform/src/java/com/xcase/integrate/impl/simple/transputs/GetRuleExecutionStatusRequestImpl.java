/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.GetRuleExecutionStatusRequest;

/**
 *
 * @author martin
 */
public class GetRuleExecutionStatusRequestImpl extends IntegrateRequestImpl implements GetRuleExecutionStatusRequest {

    private String correlationId;

    public String getCorrelationId() {
        return this.correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}

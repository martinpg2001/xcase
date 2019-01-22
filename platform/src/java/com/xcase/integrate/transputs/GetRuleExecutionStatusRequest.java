/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martin
 */
public interface GetRuleExecutionStatusRequest extends IntegrateRequest {

    public String getCorrelationId();

    public void setCorrelationId(String correlationId);
}

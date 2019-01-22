/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.UpdateRuleResponse;

/**
 *
 * @author martinpg
 */
public class UpdateRuleResponseImpl extends IntegrateResponseImpl implements UpdateRuleResponse {

    private String operationMessage;
    private String operationStatus;

    public String getOperationMessage() {
        return this.operationMessage;
    }

    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }

    public String getOperationStatus() {
        return this.operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }
}

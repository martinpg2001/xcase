/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.ExecuteRuleRequest;

/**
 *
 * @author martin
 */
public class ExecuteRuleRequestImpl extends IntegrateRequestImpl implements ExecuteRuleRequest {

    private String ruleExecutionRequest;
    private Integer ruleId;
    private Integer waitForCompletion = Integer.valueOf(30);

    public String getRuleExecutionRequest() {
        return this.ruleExecutionRequest;
    }

    public Integer getRuleId() {
        return this.ruleId;
    }

    public Integer getWaitForCompletion() {
        return this.waitForCompletion;
    }

    public void setRuleExecutionRequest(String ruleExecutionRequest) {
        this.ruleExecutionRequest = ruleExecutionRequest;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public void setWaitForCompletion(Integer waitForCompletion) {
        this.waitForCompletion = waitForCompletion;
    }
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.UpdateRuleRequest;

/**
 *
 * @author martinpg
 */
public class UpdateRuleRequestImpl extends IntegrateRequestImpl implements UpdateRuleRequest {

    private String ruleChangeRequest;
    private Integer ruleId;

    public String getRuleChangeRequest() {
        return this.ruleChangeRequest;
    }

    public void setRuleChangeRequest(String ruleChangeRequest) {
        this.ruleChangeRequest = ruleChangeRequest;
    }

    public Integer getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }
}

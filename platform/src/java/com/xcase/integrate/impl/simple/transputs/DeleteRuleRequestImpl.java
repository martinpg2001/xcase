/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.DeleteRuleRequest;

/**
 *
 * @author Administrator
 */
public class DeleteRuleRequestImpl extends IntegrateRequestImpl implements DeleteRuleRequest {

    private Integer ruleId;

    public Integer getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }
}

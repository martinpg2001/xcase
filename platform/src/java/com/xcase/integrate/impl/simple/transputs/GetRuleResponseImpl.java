/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.IntegrateRule;
import com.xcase.integrate.transputs.GetRuleResponse;

/**
 *
 * @author martin
 */
public class GetRuleResponseImpl extends IntegrateResponseImpl implements GetRuleResponse {

    private IntegrateRule rule;

    public IntegrateRule getRule() {
        return this.rule;
    }

    public void setRule(IntegrateRule rule) {
        this.rule = rule;
    }
}

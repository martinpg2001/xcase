/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.RuleProcessorStatuses;
import com.xcase.integrate.transputs.GetRuleProcessorStatusResponse;

/**
 *
 * @author martin
 */
public class GetRuleProcessorStatusResponseImpl extends IntegrateResponseImpl implements GetRuleProcessorStatusResponse {

    private RuleProcessorStatuses ruleProcessorStatuses;

    public RuleProcessorStatuses getRuleProcessorStatuses() {
        return this.ruleProcessorStatuses;
    }

    public void setRuleProcessorStatuses(RuleProcessorStatuses ruleProcessorStatuses) {
        this.ruleProcessorStatuses = ruleProcessorStatuses;
    }
}

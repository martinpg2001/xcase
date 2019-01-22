/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.RuleExecutionSummary;
import com.xcase.integrate.transputs.GetRuleSummaryResponse;

/**
 *
 * @author martin
 */
public class GetRuleSummaryResponseImpl extends IntegrateResponseImpl implements GetRuleSummaryResponse {

    private RuleExecutionSummary ruleExecutionSummary;

    public RuleExecutionSummary getRuleSummary() {
        return this.ruleExecutionSummary;
    }

    public void setRuleExecutionSummary(RuleExecutionSummary ruleExecutionSummary) {
        this.ruleExecutionSummary = ruleExecutionSummary;
    }
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.RuleErrorSummary;
import com.xcase.integrate.transputs.GetRuleErrorSummaryResponse;

/**
 *
 * @author martin
 */
public class GetRuleErrorSummaryResponseImpl extends IntegrateResponseImpl implements GetRuleErrorSummaryResponse {

    private RuleErrorSummary ruleErrorSummary;

    public RuleErrorSummary getRuleErrorSummary() {
        return this.ruleErrorSummary;
    }

    public void setRuleErrorSummary(RuleErrorSummary ruleErrorSummary) {
        this.ruleErrorSummary = ruleErrorSummary;
    }
}

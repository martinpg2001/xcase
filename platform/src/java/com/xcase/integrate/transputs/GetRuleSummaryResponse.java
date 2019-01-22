/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.RuleExecutionSummary;

/**
 *
 * @author martin
 */
public interface GetRuleSummaryResponse extends IntegrateResponse {

    public RuleExecutionSummary getRuleSummary();

    public void setRuleExecutionSummary(RuleExecutionSummary ruleExecutionSummary);
}

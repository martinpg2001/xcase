/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.RuleErrorSummary;

/**
 *
 * @author martin
 */
public interface GetRuleErrorSummaryResponse extends IntegrateResponse {

    public RuleErrorSummary getRuleErrorSummary();

    public void setRuleErrorSummary(RuleErrorSummary ruleErrorSummary);

}

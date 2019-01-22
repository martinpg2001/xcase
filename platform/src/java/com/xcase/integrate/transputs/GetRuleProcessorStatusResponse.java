/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.RuleProcessorStatuses;

/**
 *
 * @author martin
 */
public interface GetRuleProcessorStatusResponse extends IntegrateResponse {

    public RuleProcessorStatuses getRuleProcessorStatuses();

    public void setRuleProcessorStatuses(RuleProcessorStatuses ruleProcessorStatuses);
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.IntegrateRule;

/**
 *
 * @author martinpg
 */
public interface GetRuleResponse extends IntegrateResponse {

    public IntegrateRule getRule();

    public void setRule(IntegrateRule rule);
}

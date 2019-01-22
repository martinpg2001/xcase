/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.IntegrateRule;
import com.xcase.integrate.transputs.GetAllRulesResponse;

/**
 *
 * @author Administrator
 */
public class GetAllRulesResponseImpl extends IntegrateResponseImpl implements GetAllRulesResponse {

    private IntegrateRule[] rules;

    public IntegrateRule[] getRules() {
        return this.rules;
    }

    public void setRules(IntegrateRule[] rules) {
        this.rules = rules;
    }
}

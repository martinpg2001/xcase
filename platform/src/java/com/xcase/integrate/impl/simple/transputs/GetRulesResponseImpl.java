/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.IntegrateRule;
import com.xcase.integrate.transputs.GetRulesResponse;

/**
 *
 * @author martin
 */
public class GetRulesResponseImpl extends IntegrateResponseImpl implements GetRulesResponse {

    private IntegrateRule[] rules;

    public IntegrateRule[] getRules() {
        return this.rules;
    }

    public void setRules(IntegrateRule[] rules) {
        this.rules = rules;
    }
}

/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.IntegrateRule;
import com.xcase.integrate.transputs.SearchRulesResponse;

/**
 *
 * @author martin
 */
public class SearchRulesResponseImpl extends IntegrateResponseImpl implements SearchRulesResponse {
    private IntegrateRule[] rules;
    
    public IntegrateRule[] getRules() {
        return this.rules;
    }
    
    public void setRules(IntegrateRule[] rules) {
        this.rules = rules;
    }    
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.IntegrateRule;

/**
 *
 * @author martinpg
 */
public interface GetRulesResponse extends IntegrateResponse {
    
    public IntegrateRule[] getRules();
    
    public void setRules(IntegrateRule[] rules);
}

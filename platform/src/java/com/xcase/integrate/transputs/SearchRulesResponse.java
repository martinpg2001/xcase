/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.IntegrateRule;
import java.util.ArrayList;

/**
 *
 * @author martin
 */
public interface SearchRulesResponse extends IntegrateResponse {
    IntegrateRule[] getRules();
    void setRules(IntegrateRule[] rules);    
}

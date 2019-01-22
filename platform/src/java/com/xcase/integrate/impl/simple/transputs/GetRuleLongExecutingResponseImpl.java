/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.RuleLongExecuting;
import com.xcase.integrate.transputs.GetRuleLongExecutingResponse;

/**
 *
 * @author martin
 */
public class GetRuleLongExecutingResponseImpl extends IntegrateResponseImpl implements GetRuleLongExecutingResponse {

    private RuleLongExecuting ruleLongExecuting;

    public RuleLongExecuting getRuleLogExecuting() {
        return this.ruleLongExecuting;
    }

    public void setRuleLongExecuting(RuleLongExecuting ruleLongExecuting) {
        this.ruleLongExecuting = ruleLongExecuting;
    }
}

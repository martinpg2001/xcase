/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.RuleLongExecuting;

/**
 *
 * @author martin
 */
public interface GetRuleLongExecutingResponse extends IntegrateResponse {

    public RuleLongExecuting getRuleLogExecuting();

    public void setRuleLongExecuting(RuleLongExecuting ruleLongExecuting);
}

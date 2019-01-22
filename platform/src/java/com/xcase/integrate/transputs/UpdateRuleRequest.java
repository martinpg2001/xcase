/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martinpg
 */
public interface UpdateRuleRequest extends IntegrateRequest {

    public Integer getRuleId();

    public void setRuleId(Integer ruleId);

    public String getRuleChangeRequest();

    public void setRuleChangeRequest(String ruleChangeRequest);

}

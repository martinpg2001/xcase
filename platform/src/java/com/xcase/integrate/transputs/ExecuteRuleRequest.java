/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martinpg
 */
public interface ExecuteRuleRequest extends IntegrateRequest {

    public String getRuleExecutionRequest();

    public Integer getRuleId();

    public Integer getWaitForCompletion();

    public void setRuleId(Integer ruleId);

    public void setRuleExecutionRequest(String ruleExecutionRequest);

    public void setWaitForCompletion(Integer waitForCompletion);
}

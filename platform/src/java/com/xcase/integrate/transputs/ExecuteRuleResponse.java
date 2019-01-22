/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.IntegrateOutput;
import java.util.List;

/**
 *
 * @author martinpg
 */
public interface ExecuteRuleResponse extends IntegrateResponse {

    public String getCorrelationId();

    public void setCorrelationId(String correlationId);

    public String getRuleExecutionStatus();

    public void setRuleExecutionStatus(String status);

    public List<IntegrateOutput> getRuleExecutionOutputs();

    public void setRuleExecutionOutputs(List<IntegrateOutput> outputs);

    public String getRuleExecutionResultStatus();

    public void setRuleExecutionResultStatus(String ruleExecutionResultStatus);

    public String getRuleId();

    public void setRuleId(String ruleId);

    public String getRuleName();

    public void setRuleName(String ruleName);
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.IntegrateOutput;
import java.util.List;

/**
 *
 * @author martin
 */
public interface GetRuleExecutionStatusResponse extends IntegrateResponse {

    public String getCorrelationId();

    public void setCorrelationId(String correlationId);

    public String getRuleExecutionStatus();

    public void setRuleExecutionStatus(String executionStatus);

    public List<IntegrateOutput> getRuleExecutionOutputs();

    public void setRuleExecutionOutputs(List<IntegrateOutput> outputs);

    public String getRuleExecutionResultStatus();

    public void setRuleExecutionResultStatus(String resultStatus);

    public String getRuleId();

    public String getRuleName();

    public void setRuleId(String rule_id);

    public void setRuleName(String rule_name);

}

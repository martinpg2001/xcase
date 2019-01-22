/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.IntegrateOutput;
import com.xcase.integrate.transputs.ExecuteRuleResponse;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ExecuteRuleResponseImpl extends IntegrateResponseImpl implements ExecuteRuleResponse {

    private List<IntegrateOutput> outputs;
    private String correlationId;
    private String ruleExecutionStatus;
    private String ruleExecutionResultStatus;
    private String ruleId;
    private String ruleName;

    public String getCorrelationId() {
        return this.correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getRuleExecutionStatus() {
        return this.ruleExecutionStatus;
    }

    public void setRuleExecutionStatus(String ruleExecutionStatus) {
        this.ruleExecutionStatus = ruleExecutionStatus;
    }

    public List<IntegrateOutput> getRuleExecutionOutputs() {
        return this.outputs;
    }

    public void setRuleExecutionOutputs(List<IntegrateOutput> outputs) {
        this.outputs = outputs;
    }

    public String getRuleExecutionResultStatus() {
        return this.ruleExecutionResultStatus;
    }

    public void setRuleExecutionResultStatus(String ruleExecutionResultStatus) {
        this.ruleExecutionResultStatus = ruleExecutionResultStatus;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return this.ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
}

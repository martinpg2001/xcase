/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "rule_error_summary")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleErrorSummary {

    @XmlElement(name = "rule_info")
    public List<RuleInfo> ruleInfoList;

    public List<RuleInfo> getRuleInfoList() {
        return this.ruleInfoList;
    }

    public void setRuleInfoList(List<RuleInfo> ruleInfoList) {
        this.ruleInfoList = ruleInfoList;
    }
}

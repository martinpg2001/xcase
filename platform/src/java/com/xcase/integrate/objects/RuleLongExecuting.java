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
@XmlRootElement(name = "long_exe_rules")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleLongExecuting {

    @XmlElement(name = "rule_info")
    public List<RuleInfo> ruleInfoList;

    public List<RuleInfo> getRuleInfoList() {
        return this.ruleInfoList;
    }

    public void setRuleInfoList(List<RuleInfo> ruleInfoList) {
        this.ruleInfoList = ruleInfoList;
    }
}

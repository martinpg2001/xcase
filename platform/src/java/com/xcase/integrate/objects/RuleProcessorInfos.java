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
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleProcessorInfos {

    @XmlElement(name = "rp_info")
    public List<RuleProcessorStatus> ruleProcessorStatusList;

    public List<RuleProcessorStatus> getRuleProcessorStatusList() {
        return this.ruleProcessorStatusList;
    }

    public void setRuleProcessorStatusList(List<RuleProcessorStatus> ruleProcessorStatusList) {
        this.ruleProcessorStatusList = ruleProcessorStatusList;
    }
}

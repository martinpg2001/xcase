/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "rule_processor_cpu_usages")
public class RuleProcessorStatuses {

    @XmlElement(name = "rp_infos")
    public RuleProcessorInfos ruleProcessorInfos;

    @XmlElement(name = "total_rule_processor_cpu_usage")
    public String totalRuleProcessorCPUUsage;
}

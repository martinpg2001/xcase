/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "rule_execution_summary")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleExecutionSummary {

    @XmlElement(name = "status_summary")
    public StatusSummary statusSummary;
}

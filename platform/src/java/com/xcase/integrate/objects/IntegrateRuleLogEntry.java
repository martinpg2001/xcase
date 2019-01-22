/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "RULE")
public class IntegrateRuleLogEntry extends IntegrateLogEntry implements LogEntry {

    @XmlElement(name = "rule")
    public RuleLogEntry rule;
}

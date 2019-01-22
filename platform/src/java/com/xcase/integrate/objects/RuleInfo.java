/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleInfo {

    @XmlElement(name = "rule_id")
    public String rule_id;

    @XmlElement(name = "rule_name")
    public String rule_name;

    @XmlElement(name = "execution_times")
    public String execution_times;
}

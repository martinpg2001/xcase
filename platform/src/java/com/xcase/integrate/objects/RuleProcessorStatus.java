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
public class RuleProcessorStatus {

    public String cpu_usage;

    public String rule_id;

    public String rp_id;

    public String rule_name;
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author martinpg
 */
@XmlRootElement(name = "rules")
public class IntegrateRuleList {

    List<IntegrateRule> rules;

    public List<IntegrateRule> getRules() {
        return this.rules;
    }

    @XmlElement(name = "rule")
    public void setRules(List<IntegrateRule> rules) {
        this.rules = rules;
    }
}

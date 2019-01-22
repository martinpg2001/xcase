/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author martinpg
 */
@XmlRootElement(name = "results")
public class RuleResults {

    @XmlElementWrapper(name = "outputs")
    @XmlElement(name = "output")
    public List<IntegrateOutput> outputs;

    @XmlAttribute(name = "rule_id")
    public String rule_id;

    @XmlAttribute(name = "rule_name")
    public String rule_name;
}

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
@XmlRootElement(name = "rule_execution_request")
public class RuleExecutionRequest {

    @XmlAttribute(name = "xmlns")
    public String xmlns = "http://intapp/integrate/api/v1";

    @XmlElementWrapper(name = "inputs")
    @XmlElement(name = "input")
    public List<IntegrateInput> inputs;
}

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
@XmlRootElement(name = "rule_execution_result")
public class RuleExecutionResponse {

    @XmlElement(name = "correlation_id")
    public String correlation_id;

    @XmlElement(name = "execution_status")
    public String execution_status;

    @XmlElement(name = "outputs")
    public IntegrateOutputList outputsXML;

    public List<IntegrateOutput> outputs;

    @XmlElement(name = "result_status")
    public String result_status;

    public int rule_id;

    public String rule_name;
}

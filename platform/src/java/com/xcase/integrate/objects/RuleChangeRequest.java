/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martinpg
 */
@XmlRootElement(name = "rule_change_request")
public class RuleChangeRequest {

    @XmlAttribute(name = "xmlns")
    public String xmlns = "http://intapp/integrate/api/v1";

    @XmlElement(name = "name")
    public String name;

    @XmlElement(name = "owner")
    public String owner;

    @XmlElement(name = "location")
    public String[] location;

    @XmlElement(name = "mode")
    public String mode;
}

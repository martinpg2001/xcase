/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martinpg
 */
public class IntegrateOutput {

    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "type")
    public String type;

    @XmlValue
    public String value;

    public IntegrateOutput() {

    }

    public IntegrateOutput(String name, String type) {
        this.name = name;
        this.type = type;
    }
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martinpg
 */
public class IntegrateInput {

    @XmlAttribute(name = "name")
    public String name;

    @XmlValue
    public String value;

    public IntegrateInput() {

    }

    public IntegrateInput(String name, String value) {
        this.name = name;
        this.value = value;
    }
}

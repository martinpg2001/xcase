/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
public class SystemLogEntry {

    @XmlAttribute(name = "extra1")
    public String extra1;

    @XmlAttribute(name = "extra2")
    public String extra2;

    @XmlAttribute(name = "level")
    public String level;

    @XmlAttribute(name = "message")
    public String message;
}

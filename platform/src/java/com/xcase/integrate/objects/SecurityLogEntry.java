/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
public class SecurityLogEntry {

    @XmlAttribute(name = "ip")
    public String ip;

    @XmlAttribute(name = "message")
    public String message;

    @XmlAttribute(name = "system")
    public String system;

    @XmlAttribute(name = "time")
    public String time;
}

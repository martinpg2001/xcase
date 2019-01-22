/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
public class WebServiceLogEntry {

    @XmlAttribute(name = "alias")
    public String alias;

    @XmlAttribute(name = "correlation")
    public String correlation;

    @XmlAttribute(name = "operation")
    public String operation;

    @XmlAttribute(name = "protocol")
    public String protocol;

    @XmlAttribute(name = "session")
    public String session;
}

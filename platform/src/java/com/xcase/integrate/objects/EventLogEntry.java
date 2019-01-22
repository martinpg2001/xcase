/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
public class EventLogEntry {

    @XmlAttribute(name = "continuation")
    public String continuation;

    @XmlAttribute(name = "datasourceName")
    public String datasourceName;

    @XmlAttribute(name = "eventID")
    public String eventID;

    @XmlAttribute(name = "name")
    public String name;
}

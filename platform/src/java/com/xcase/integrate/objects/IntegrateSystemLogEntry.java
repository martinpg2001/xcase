/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "SYSTEMS")
public class IntegrateSystemLogEntry extends IntegrateLogEntry implements LogEntry {

    @XmlElement(name = "systems")
    public SystemLogEntry systems;
}

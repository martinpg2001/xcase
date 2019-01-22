/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "EVENT")
public class IntegrateEventLogEntry extends IntegrateLogEntry implements LogEntry {

    @XmlElement(name = "event")
    public EventLogEntry event;
}

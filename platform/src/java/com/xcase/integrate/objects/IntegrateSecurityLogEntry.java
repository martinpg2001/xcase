/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "SECURITY")
public class IntegrateSecurityLogEntry extends IntegrateLogEntry implements LogEntry {

    @XmlElement(name = "security")
    public SecurityLogEntry security;
}

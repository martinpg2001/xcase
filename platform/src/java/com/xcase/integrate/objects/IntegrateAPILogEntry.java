/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "API")
public class IntegrateAPILogEntry extends IntegrateLogEntry implements LogEntry {

    @XmlElement(name = "api_server")
    public APILogEntry api;
}

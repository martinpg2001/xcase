/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "WEBSERVICE")
public class IntegrateWebServiceLogEntry extends IntegrateLogEntry implements LogEntry {

    @XmlElement(name = "webservice")
    public WebServiceLogEntry webservice;
}

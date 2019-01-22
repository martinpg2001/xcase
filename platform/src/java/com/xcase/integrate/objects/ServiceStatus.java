/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceStatus {

    @XmlElement(name = "process_id")
    public String process_id;

    @XmlElement(name = "service_name")
    public String service_name;

    @XmlElement(name = "status")
    public String status;
}

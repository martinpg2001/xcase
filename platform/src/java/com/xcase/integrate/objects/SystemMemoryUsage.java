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
@XmlRootElement(name = "memory_usage")
public class SystemMemoryUsage {

    @XmlElement(name = "free_ram")
    public String free_ram;

    @XmlElement(name = "free_swap")
    public String free_swap;

    @XmlElement(name = "timestamp")
    public String timestamp;

    @XmlElement(name = "used_ram")
    public String used_ram;

    @XmlElement(name = "used_swap")
    public String used_swap;
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "disk_space")
public class SystemDiskUsage {

    @XmlElement(name = "free_space")
    public String free_space;

    @XmlElement(name = "timestamp")
    public String timestamp;

    @XmlElement(name = "total_space")
    public String total_space;

    @XmlElement(name = "used_space")
    public String used_space;
}

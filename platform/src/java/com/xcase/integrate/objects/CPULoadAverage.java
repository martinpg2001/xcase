/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martinpg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CPULoadAverage {

    @XmlElement(name = "cpu_load_avg_15min")
    public String cpu_load_avg_15min;

    @XmlElement(name = "cpu_load_avg_1min")
    public String cpu_load_avg_1min;

    @XmlElement(name = "cpu_load_avg_5min")
    public String cpu_load_avg_5min;

    @XmlElement(name = "timestamp")
    public String timestamp;
}

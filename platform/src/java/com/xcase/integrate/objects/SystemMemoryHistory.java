/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author martinpg
 */
@XmlRootElement(name = "memory_usages")
@XmlAccessorType(XmlAccessType.FIELD)
public class SystemMemoryHistory {

    @XmlElement(name = "memory_usage")
    public List<SystemMemoryUsage> systemMemoryUsageList;

    public List<SystemMemoryUsage> getSystemMemoryUsageList() {
        return this.systemMemoryUsageList;
    }

    public void setSystemMemoryUsageList(List<SystemMemoryUsage> systemMemoryUsageList) {
        this.systemMemoryUsageList = systemMemoryUsageList;
    }
}

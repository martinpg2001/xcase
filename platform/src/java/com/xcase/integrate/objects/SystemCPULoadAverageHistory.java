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
@XmlRootElement(name = "cpu_load_avgs")
@XmlAccessorType(XmlAccessType.FIELD)
public class SystemCPULoadAverageHistory {

    @XmlElement(name = "cpu_load_avg")
    public List<CPULoadAverage> cpuLoadAverageList;

    public List<CPULoadAverage> getAgentActivityList() {
        return this.cpuLoadAverageList;
    }

    public void setAgentActivityList(List<CPULoadAverage> cpuLoadAverageList) {
        this.cpuLoadAverageList = cpuLoadAverageList;
    }
}

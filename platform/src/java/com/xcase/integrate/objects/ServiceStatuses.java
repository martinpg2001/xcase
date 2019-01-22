/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "service_statuses")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceStatuses {

    @XmlElement(name = "service_status")
    public List<ServiceStatus> serviceStatusList;

    public List<ServiceStatus> getServiceStatusList() {
        return this.serviceStatusList;
    }

    public void setServiceStatusList(List<ServiceStatus> serviceStatusList) {
        this.serviceStatusList = serviceStatusList;
    }
}

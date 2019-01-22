/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.ServiceStatuses;
import com.xcase.integrate.transputs.GetServiceStatusResponse;

/**
 *
 * @author martin
 */
public class GetServiceStatusResponseImpl extends IntegrateResponseImpl implements GetServiceStatusResponse {

    private ServiceStatuses serviceStatuses;

    public ServiceStatuses getServiceStatuses() {
        return this.serviceStatuses;
    }

    public void setServiceStatuses(ServiceStatuses serviceStatuses) {
        this.serviceStatuses = serviceStatuses;
    }
}

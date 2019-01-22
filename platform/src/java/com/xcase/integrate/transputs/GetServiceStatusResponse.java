/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.ServiceStatuses;

/**
 *
 * @author martin
 */
public interface GetServiceStatusResponse extends IntegrateResponse {

    public ServiceStatuses getServiceStatuses();

    public void setServiceStatuses(ServiceStatuses serviceStatuses);
}

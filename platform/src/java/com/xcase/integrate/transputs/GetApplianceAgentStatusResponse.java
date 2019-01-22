/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.ApplianceAgentActivities;

/**
 *
 * @author martin
 */
public interface GetApplianceAgentStatusResponse extends IntegrateResponse {

    public ApplianceAgentActivities getApplianceAgentActivities();

    public void setApplianceAgentActivities(ApplianceAgentActivities applianceAgentActivities);
}

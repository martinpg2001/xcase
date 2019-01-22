/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.ApplianceAgentActivities;
import com.xcase.integrate.transputs.GetApplianceAgentStatusResponse;

/**
 *
 * @author Administrator
 */
public class GetApplianceAgentStatusResponseImpl extends IntegrateResponseImpl implements GetApplianceAgentStatusResponse {

    private ApplianceAgentActivities applianceAgentActivities;

    public ApplianceAgentActivities getApplianceAgentActivities() {
        return this.applianceAgentActivities;
    }

    public void setApplianceAgentActivities(ApplianceAgentActivities applianceAgentActivities) {
        this.applianceAgentActivities = applianceAgentActivities;
    }
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.SystemDiskUsage;
import com.xcase.integrate.transputs.GetSystemDiskUsageResponse;

/**
 *
 * @author martin
 */
public class GetSystemDiskUsageResponseImpl extends IntegrateResponseImpl implements GetSystemDiskUsageResponse {

    private SystemDiskUsage systemDiskUsage;

    public SystemDiskUsage getSystemDiskUsage() {
        return this.systemDiskUsage;
    }

    public void setSystemDiskUsage(SystemDiskUsage systemDiskUsage) {
        this.systemDiskUsage = systemDiskUsage;
    }
}

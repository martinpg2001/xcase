/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.SystemMemoryUsage;
import com.xcase.integrate.transputs.GetSystemMemoryUsageResponse;

/**
 *
 * @author martin
 */
public class GetSystemMemoryUsageResponseImpl extends IntegrateResponseImpl implements GetSystemMemoryUsageResponse {

    private SystemMemoryUsage systemMemoryUsage;

    public SystemMemoryUsage getSystemMemoryUsage() {
        return this.systemMemoryUsage;
    }

    public void setSystemMemoryUsage(SystemMemoryUsage systemMemoryUsage) {
        this.systemMemoryUsage = systemMemoryUsage;
    }
}

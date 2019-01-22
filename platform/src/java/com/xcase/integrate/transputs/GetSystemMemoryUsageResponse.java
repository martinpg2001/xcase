/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.SystemMemoryUsage;

/**
 *
 * @author martin
 */
public interface GetSystemMemoryUsageResponse extends IntegrateResponse {

    public SystemMemoryUsage getSystemMemoryUsage();

    public void setSystemMemoryUsage(SystemMemoryUsage systemMemoryUsage);
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.SystemDiskUsage;

/**
 *
 * @author martin
 */
public interface GetSystemDiskUsageResponse extends IntegrateResponse {

    public SystemDiskUsage getSystemDiskUsage();

    public void setSystemDiskUsage(SystemDiskUsage systemDiskUsage);

}

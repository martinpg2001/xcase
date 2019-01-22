/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.SystemCPULoadAverageHistory;

/**
 *
 * @author martinpg
 */
public interface GetSystemCPULoadAverageHistoryResponse extends IntegrateResponse {

    public SystemCPULoadAverageHistory getSystemCPULoadAverageHistory();

    public void setSystemCPULoadAverageHistory(SystemCPULoadAverageHistory systemCPULoadAverageHistory);
}

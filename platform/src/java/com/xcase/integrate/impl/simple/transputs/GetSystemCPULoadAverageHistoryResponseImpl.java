/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.SystemCPULoadAverageHistory;
import com.xcase.integrate.transputs.GetSystemCPULoadAverageHistoryResponse;

/**
 *
 * @author martin
 */
public class GetSystemCPULoadAverageHistoryResponseImpl extends IntegrateResponseImpl implements GetSystemCPULoadAverageHistoryResponse {

    private SystemCPULoadAverageHistory systemCPULoadAverageHistory;

    public SystemCPULoadAverageHistory getSystemCPULoadAverageHistory() {
        return this.systemCPULoadAverageHistory;
    }

    public void setSystemCPULoadAverageHistory(SystemCPULoadAverageHistory systemCPULoadAverageHistory) {
        this.systemCPULoadAverageHistory = systemCPULoadAverageHistory;
    }
}

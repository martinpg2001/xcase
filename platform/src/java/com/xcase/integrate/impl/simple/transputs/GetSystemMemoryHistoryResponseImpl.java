/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.SystemMemoryHistory;
import com.xcase.integrate.transputs.GetSystemMemoryHistoryResponse;

/**
 *
 * @author martin
 */
public class GetSystemMemoryHistoryResponseImpl extends IntegrateResponseImpl implements GetSystemMemoryHistoryResponse {

    private SystemMemoryHistory systemMemoryHistory;

    public SystemMemoryHistory getSystemMemoryHistory() {
        return this.systemMemoryHistory;
    }

    public void setSystemMemoryHistory(SystemMemoryHistory systemMemoryHistory) {
        this.systemMemoryHistory = systemMemoryHistory;
    }
}

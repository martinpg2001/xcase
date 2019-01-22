/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.SystemMemoryHistory;

/**
 *
 * @author martinpg
 */
public interface GetSystemMemoryHistoryResponse extends IntegrateResponse {

    public SystemMemoryHistory getSystemMemoryHistory();

    public void setSystemMemoryHistory(SystemMemoryHistory systemMemoryHistory);
}

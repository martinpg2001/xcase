/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.IntegrateBulkLogEntry;

/**
 *
 * @author martin
 */
public interface GetLogsResponse extends IntegrateResponse {

    public IntegrateBulkLogEntry[] getLogEntries();

    public void setLogEntries(IntegrateBulkLogEntry[] logEntries);

    public String getNextPageStartsAt();

    public void setNextPageStartsAt(String nextPageStartsAt);
}

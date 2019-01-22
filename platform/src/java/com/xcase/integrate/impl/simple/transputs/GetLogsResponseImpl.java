/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.IntegrateBulkLogEntry;
import com.xcase.integrate.transputs.GetLogsResponse;

/**
 *
 * @author martin
 */
public class GetLogsResponseImpl extends IntegrateResponseImpl implements GetLogsResponse {

    private IntegrateBulkLogEntry[] logEntries;
    private String nextPageStartsAt;

    public IntegrateBulkLogEntry[] getLogEntries() {
        return this.logEntries;
    }

    public void setLogEntries(IntegrateBulkLogEntry[] logEntries) {
        this.logEntries = logEntries;
    }

    public String getNextPageStartsAt() {
        return this.nextPageStartsAt;
    }

    public void setNextPageStartsAt(String nextPageStartsAt) {
        this.nextPageStartsAt = nextPageStartsAt;
    }
}

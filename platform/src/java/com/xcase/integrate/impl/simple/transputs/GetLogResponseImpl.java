/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.LogEntry;
import com.xcase.integrate.objects.IntegrateLogAction;
import com.xcase.integrate.transputs.GetLogResponse;

/**
 *
 * @author martin
 */
public class GetLogResponseImpl extends IntegrateResponseImpl implements GetLogResponse {

    private IntegrateLogAction[] logActions;
    private LogEntry logEntry;

    public IntegrateLogAction[] getLogActions() {
        return this.logActions;
    }

    public void setLogActions(IntegrateLogAction[] logActions) {
        this.logActions = logActions;
    }

    public LogEntry getLogEntry() {
        return this.logEntry;
    }

    public void setLogEntry(LogEntry logEntry) {
        this.logEntry = logEntry;
    }
}

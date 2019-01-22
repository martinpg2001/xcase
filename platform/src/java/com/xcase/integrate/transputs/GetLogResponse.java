/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.LogEntry;
import com.xcase.integrate.objects.IntegrateLogAction;

/**
 *
 * @author martin
 */
public interface GetLogResponse extends IntegrateResponse {

    public IntegrateLogAction[] getLogActions();

    public void setLogActions(IntegrateLogAction[] logActions);

    public LogEntry getLogEntry();

    public void setLogEntry(LogEntry logEntry);

}

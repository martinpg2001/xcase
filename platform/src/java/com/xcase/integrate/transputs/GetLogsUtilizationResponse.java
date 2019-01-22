/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.IncreasedLogEntries7Days;
import com.xcase.integrate.objects.JsonIncreasedLogEntries7Days;

/**
 *
 * @author martin
 */
public interface GetLogsUtilizationResponse extends IntegrateResponse {

    public IncreasedLogEntries7Days getIncreasedLogEntries7Days();

    public void setIncreasedLogEntries7Days(IncreasedLogEntries7Days increasedLogEntries7Days);

    public JsonIncreasedLogEntries7Days getJsonIncreasedLogEntries7Days();

    public void setJsonIncreasedLogEntries7Days(JsonIncreasedLogEntries7Days increasedLogEntries7Days);
}

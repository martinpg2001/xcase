/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.IncreasedLogEntries7Days;
import com.xcase.integrate.objects.JsonIncreasedLogEntries7Days;
import com.xcase.integrate.transputs.GetLogsUtilizationResponse;

/**
 *
 * @author martin
 */
public class GetLogsUtilizationResponseImpl extends IntegrateResponseImpl implements GetLogsUtilizationResponse {

    private IncreasedLogEntries7Days increasedLogEntries7Days;
    private JsonIncreasedLogEntries7Days jsonIncreasedLogEntries7Days;

    public IncreasedLogEntries7Days getIncreasedLogEntries7Days() {
        return this.increasedLogEntries7Days;
    }

    public void setIncreasedLogEntries7Days(IncreasedLogEntries7Days increasedLogEntries7Days) {
        this.increasedLogEntries7Days = increasedLogEntries7Days;
    }

    public JsonIncreasedLogEntries7Days getJsonIncreasedLogEntries7Days() {
        return this.jsonIncreasedLogEntries7Days;
    }

    public void setJsonIncreasedLogEntries7Days(JsonIncreasedLogEntries7Days jsonIncreasedLogEntries7Days) {
        this.jsonIncreasedLogEntries7Days = jsonIncreasedLogEntries7Days;
    }
}

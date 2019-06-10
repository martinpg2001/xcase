package com.xcase.azure;

import com.xcase.azure.transputs.*;

public interface AzureExternalAPI {
    GetEventsResponse getEvents(GetEventsRequest getEventsRequest);
}

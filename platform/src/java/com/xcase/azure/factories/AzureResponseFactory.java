package com.xcase.azure.factories;

import com.xcase.azure.transputs.*;

public class AzureResponseFactory extends BaseAzureFactory {

    public static GetEventsResponse createGetEventsResponse() {
        Object obj = newInstanceOf("azure.config.responsefactory.GetEventsResponse");
        return (GetEventsResponse) obj;
    }

}

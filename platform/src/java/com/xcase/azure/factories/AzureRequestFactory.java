package com.xcase.azure.factories;

import com.xcase.azure.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AzureRequestFactory extends BaseAzureFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create request object.
     *
     * @return request object
     */
    public static GetEventsRequest createGetEventsRequest() {
        Object obj = newInstanceOf("azure.config.requestfactory.GetEventsRequest");
        return (GetEventsRequest) obj;
    }
}

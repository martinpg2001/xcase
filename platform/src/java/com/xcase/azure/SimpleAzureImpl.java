package com.xcase.azure;

import com.xcase.azure.impl.simple.core.AzureConfigurationManager;
import com.xcase.azure.impl.simple.methods.GetEventsMethod;
import com.xcase.azure.transputs.GetEventsRequest;
import com.xcase.azure.transputs.GetEventsResponse;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleAzureImpl implements AzureExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public AzureConfigurationManager LocalConfigurationManager = AzureConfigurationManager.getConfigurationManager();
    
    private GetEventsMethod getEventsMethod = new GetEventsMethod();
    
    @Override
    public GetEventsResponse getEvents(GetEventsRequest getEventsRequest) {
        // TODO Auto-generated method stub
        return this.getEventsMethod.getEvents(getEventsRequest);
    }

}

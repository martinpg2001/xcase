package com.xcase.intapp.time;

import com.xcase.intapp.time.impl.simple.core.TimeConfigurationManager;
import com.xcase.intapp.time.impl.simple.methods.*;
import com.xcase.intapp.time.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTimeImpl implements TimeExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public TimeConfigurationManager localConfigurationManager = TimeConfigurationManager.getConfigurationManager();

    /**
     * method implementation.
     */
    private GetClientsMethod getClientsMethod = new GetClientsMethod();
    
    /**
     * method implementation.
     */
    private GetRestrictedTextsMethod getRestrictedTextsMethod = new GetRestrictedTextsMethod();
    
    @Override
	public GetClientsResponse getClients(GetClientsRequest getClientsRequest) {
    	return this.getClientsMethod.getClients(getClientsRequest);
	}

    @Override
	public GetRestrictedTextsResponse getRestrictedTexts(GetRestrictedTextsRequest getRestrictedTextsRequest) {
    	return this.getRestrictedTextsMethod.getRestrictedTexts(getRestrictedTextsRequest);
	}
}

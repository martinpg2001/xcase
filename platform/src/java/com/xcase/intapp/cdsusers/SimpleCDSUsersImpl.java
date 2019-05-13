package com.xcase.intapp.cdsusers;

import com.xcase.intapp.cdscm.impl.simple.methods.CreateClientMethod;
import com.xcase.intapp.cdsusers.impl.simple.core.CDSUsersConfigurationManager;
import com.xcase.intapp.cdsusers.impl.simple.methods.CreatePersonMethod;
import com.xcase.intapp.cdsusers.transputs.CreatePersonRequest;
import com.xcase.intapp.cdsusers.transputs.CreatePersonResponse;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleCDSUsersImpl implements CDSUsersExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public CDSUsersConfigurationManager localConfigurationManager = CDSUsersConfigurationManager.getConfigurationManager();
	
    /**
     * method implementation.
     */
    private CreatePersonMethod createPersonMethod = new CreatePersonMethod();
    
    @Override
	public CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest) {
    	return this.createPersonMethod.createPerson(createPersonRequest);
	}

}

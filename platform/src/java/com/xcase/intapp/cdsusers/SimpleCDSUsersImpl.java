package com.xcase.intapp.cdsusers;

import com.xcase.intapp.cdscm.impl.simple.methods.CreateClientMethod;
import com.xcase.intapp.cdsusers.impl.simple.core.CDSUsersConfigurationManager;
import com.xcase.intapp.cdsusers.impl.simple.methods.CreatePersonMethod;
import com.xcase.intapp.cdsusers.impl.simple.methods.CreateUserMethod;
import com.xcase.intapp.cdsusers.impl.simple.methods.GetPersonsMethod;
import com.xcase.intapp.cdsusers.transputs.*;
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
    
    /**
     * method implementation.
     */
    private CreateUserMethod createUserMethod = new CreateUserMethod();
    
    /**
     * method implementation.
     */
    private GetPersonsMethod getPersonsMethod = new GetPersonsMethod();
    
    @Override
	public CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest) {
    	return this.createPersonMethod.createPerson(createPersonRequest);
	}
    
    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        return this.createUserMethod.createUser(createUserRequest);
    }
    
    @Override
    public GetPersonsResponse getPersons(GetPersonsRequest getPersonsRequest) {
        return this.getPersonsMethod.getPersons(getPersonsRequest);
    }
}

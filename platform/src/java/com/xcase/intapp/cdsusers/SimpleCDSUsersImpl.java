package com.xcase.intapp.cdsusers;

import com.xcase.intapp.cdsusers.impl.simple.core.CDSUsersConfigurationManager;
import com.xcase.intapp.cdsusers.impl.simple.methods.*;
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
    private CreateRoleMethod createRoleMethod = new CreateRoleMethod();
    
    /**
     * method implementation.
     */
    private CreateServiceUserMethod createServiceUserMethod = new CreateServiceUserMethod();
    
    /**
     * method implementation.
     */
    private CreateUserMethod createUserMethod = new CreateUserMethod();
    
    /**
     * method implementation.
     */
    private DeleteRoleMethod deleteRoleMethod = new DeleteRoleMethod();
    
    /**
     * method implementation.
     */
    private FindCapabilitiesMethod findCapabilitiesMethod = new FindCapabilitiesMethod();
    
    /**
     * method implementation.
     */
    private FindRolesMethod findRolesMethod = new FindRolesMethod();
    
    /**
     * method implementation.
     */
    private FindServiceUsersMethod findServiceUsersMethod = new FindServiceUsersMethod();
    
    /**
     * method implementation.
     */
    private FindUsersMethod findUsersMethod = new FindUsersMethod();
    
    /**
     * method implementation.
     */
    private GetCapabilityMethod getCapabilityMethod = new GetCapabilityMethod();
    
    /**
     * method implementation.
     */
    private GetPersonsMethod getPersonsMethod = new GetPersonsMethod();
    
    /**
     * method implementation.
     */
    private GetServiceUserMethod getServiceUserMethod = new GetServiceUserMethod();
    
    /**
     * method implementation.
     */
    private GetUserMethod getUserMethod = new GetUserMethod();
    
    /**
     * method implementation.
     */
    private PartiallyUpdateUserMethod partiallyUpdateUserMethod = new PartiallyUpdateUserMethod();
    
    /**
     * method implementation.
     */
    private PublishEntitiesMethod publishEntitiesMethod = new PublishEntitiesMethod();
    
    /**
     * method implementation.
     */
    private PutUserMethod putUserMethod = new PutUserMethod();
    
    /**
     * method implementation.
     */
    private SetRoleUsersMethod setRoleUsersMethod = new SetRoleUsersMethod();
    
    @Override
	public CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest) {
    	return this.createPersonMethod.createPerson(createPersonRequest);
	}
    
    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        return this.createUserMethod.createUser(createUserRequest);
    }
    
    @Override
    public FindUsersResponse findUsers(FindUsersRequest findUsersRequest) {
        return this.findUsersMethod.findUsers(findUsersRequest);
    }
    
    @Override
    public GetPersonsResponse getPersons(GetPersonsRequest getPersonsRequest) {
        return this.getPersonsMethod.getPersons(getPersonsRequest);
    }

    @Override
    public PartiallyUpdateUserResponse partiallyUpdateUser(PartiallyUpdateUserRequest partiallyUpdateUserRequest) {
        return this.partiallyUpdateUserMethod.partiallyUpdateUser(partiallyUpdateUserRequest);
    }
    
    @Override
    public PutUserResponse putUser(PutUserRequest putUserRequest) {
        return this.putUserMethod.putUser(putUserRequest);
    }

	@Override
	public GetUserResponse getUser(GetUserRequest getUserRequest) {
        return this.getUserMethod.getUser(getUserRequest);
	}

    @Override
    public FindRolesResponse findRoles(FindRolesRequest findRolesRequest) {
        return this.findRolesMethod.findRoles(findRolesRequest);
    }

    @Override
    public FindCapabilitiesResponse findCapabilities(FindCapabilitiesRequest findCapabilitiesRequest) {
        return this.findCapabilitiesMethod.findCapabilities(findCapabilitiesRequest);
    }

    @Override
    public GetCapabilityResponse getCapability(GetCapabilityRequest getCapabilityRequest) {
        return this.getCapabilityMethod.getCapability(getCapabilityRequest);
    }

	@Override
	public PublishEntitiesResponse publishEntities(PublishEntitiesRequest publishEntitiesRequest) {
		return this.publishEntitiesMethod.publishEntities(publishEntitiesRequest);
	}

	@Override
	public FindServiceUsersResponse findServiceUsers(FindServiceUsersRequest findServiceUsersRequest) {
		return this.findServiceUsersMethod.findServiceUsers(findServiceUsersRequest);
	}

	@Override
	public GetServiceUserResponse getServiceUser(GetServiceUserRequest getServiceUserRequest) {
		return this.getServiceUserMethod.getServiceUser(getServiceUserRequest);
	}

    @Override
    public CreateServiceUserResponse createServiceUser(CreateServiceUserRequest createServiceUserRequest) {
        return this.createServiceUserMethod.createServiceUser(createServiceUserRequest);
    }

    @Override
    public CreateRoleResponse createRole(CreateRoleRequest createRoleRequest) {
        return this.createRoleMethod.createRole(createRoleRequest);
    }

    @Override
    public DeleteRoleResponse deleteRole(DeleteRoleRequest deleteRoleRequest) {
        return this.deleteRoleMethod.deleteRole(deleteRoleRequest);
    }

    @Override
    public SetRoleUsersResponse setRoleUsers(SetRoleUsersRequest setRoleUsersRequest) {
        return this.setRoleUsersMethod.setRoleUsers(setRoleUsersRequest);
    }
}

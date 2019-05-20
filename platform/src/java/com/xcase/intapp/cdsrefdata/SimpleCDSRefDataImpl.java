package com.xcase.intapp.cdsrefdata;

import com.xcase.intapp.cdsrefdata.impl.simple.core.CDSRefDataConfigurationManager;
import com.xcase.intapp.cdsrefdata.impl.simple.methods.*;
import com.xcase.intapp.cdsrefdata.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleCDSRefDataImpl implements CDSRefDataExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public CDSRefDataConfigurationManager localConfigurationManager = CDSRefDataConfigurationManager.getConfigurationManager();
 
    /**
     * method implementation.
     */
    private CreateMatterStatusMethod createMatterStatusMethod = new CreateMatterStatusMethod();
    
    /**
     * method implementation.
     */
    private FindDepartmentsMethod findDepartmentsMethod = new FindDepartmentsMethod();
    
    /**
     * method implementation.
     */
    private FindTypesMethod findTypesMethod = new FindTypesMethod();
    
    /**
     * method implementation.
     */
    private GetClientStatusesMethod getClientStatusesMethod = new GetClientStatusesMethod();
    
    /**
     * method implementation.
     */
    private GetMatterStatusesMethod getMatterStatusesMethod = new GetMatterStatusesMethod();
    
    /**
     * method implementation.
     */
    private GetTypeByKeyMethod getTypeByKeyMethod = new GetTypeByKeyMethod();
    
    /**
     * method implementation.
     */
    private PatchTypesMethod patchTypesMethod = new PatchTypesMethod();
    
    /**
     * method implementation.
     */
    private PostTypesMethod postTypesMethod = new PostTypesMethod();
    
    @Override
    public CreateMatterStatusResponse createMatterStatus(CreateMatterStatusRequest createMatterStatusRequest) {
        return this.createMatterStatusMethod.createMatterStatus(createMatterStatusRequest);
    }

    @Override
    public GetClientStatusesResponse getClientStatuses(GetClientStatusesRequest getClientStatusesRequest) {
        return this.getClientStatusesMethod.getClientStatuses(getClientStatusesRequest);
    }
    
    @Override
    public GetMatterStatusesResponse getMatterStatuses(GetMatterStatusesRequest getMatterStatusesRequest) {
        return this.getMatterStatusesMethod.getMatterStatuses(getMatterStatusesRequest);
    }

	@Override
	public FindDepartmentsResponse findDepartments(FindDepartmentsRequest findDepartmentsRequest) {
		return this.findDepartmentsMethod.findDepartments(findDepartmentsRequest);
	}

	@Override
	public FindTypesResponse findTypes(FindTypesRequest findTypesRequest) {
		return this.findTypesMethod.findTypes(findTypesRequest);
	}

	@Override
	public GetTypeByKeyResponse getTypeByKey(GetTypeByKeyRequest getTypeByKeyRequest) {
		return this.getTypeByKeyMethod.getTypeByKey(getTypeByKeyRequest);
	}

	@Override
	public PatchTypesResponse patchTypes(PatchTypesRequest patchTypesRequest) {
		return this.patchTypesMethod.patchTypes(patchTypesRequest);
	}

	@Override
	public PostTypesResponse postTypes(PostTypesRequest postTypesRequest) {
		return this.postTypesMethod.postTypes(postTypesRequest);
	}

}

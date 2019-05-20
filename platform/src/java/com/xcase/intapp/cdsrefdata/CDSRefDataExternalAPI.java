package com.xcase.intapp.cdsrefdata;

import com.xcase.intapp.cdsrefdata.transputs.*;

public interface CDSRefDataExternalAPI {

    GetClientStatusesResponse getClientStatuses(GetClientStatusesRequest getClientStatusesRequest);

	GetMatterStatusesResponse getMatterStatuses(GetMatterStatusesRequest getMatterStatusesRequest);

	CreateMatterStatusResponse createMatterStatus(CreateMatterStatusRequest createMatterStatusRequest);

	FindDepartmentsResponse findDepartments(FindDepartmentsRequest findDepartmentsRequest);

	FindTypesResponse findTypes(FindTypesRequest findTypesRequest);

	GetTypeByKeyResponse getTypeByKey(GetTypeByKeyRequest getTypeByKeyRequest);

}

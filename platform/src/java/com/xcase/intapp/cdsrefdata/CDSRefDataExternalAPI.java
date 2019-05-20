package com.xcase.intapp.cdsrefdata;

import com.xcase.intapp.cdsrefdata.transputs.CreateMatterStatusRequest;
import com.xcase.intapp.cdsrefdata.transputs.CreateMatterStatusResponse;
import com.xcase.intapp.cdsrefdata.transputs.FindDepartmentsRequest;
import com.xcase.intapp.cdsrefdata.transputs.FindDepartmentsResponse;
import com.xcase.intapp.cdsrefdata.transputs.FindTypesRequest;
import com.xcase.intapp.cdsrefdata.transputs.FindTypesResponse;
import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesRequest;
import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesResponse;
import com.xcase.intapp.cdsrefdata.transputs.GetMatterStatusesRequest;
import com.xcase.intapp.cdsrefdata.transputs.GetMatterStatusesResponse;

public interface CDSRefDataExternalAPI {

    GetClientStatusesResponse getClientStatuses(GetClientStatusesRequest getClientStatusesRequest);

	GetMatterStatusesResponse getMatterStatuses(GetMatterStatusesRequest getMatterStatusesRequest);

	CreateMatterStatusResponse createMatterStatus(CreateMatterStatusRequest createMatterStatusRequest);

	FindDepartmentsResponse findDepartments(FindDepartmentsRequest findDepartmentsRequest);

	FindTypesResponse findTypes(FindTypesRequest findTypesRequest);

}

package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import com.xcase.intapp.cdsrefdata.transputs.GetMatterStatusesRequest;

public class GetMatterStatusesRequestImpl extends CDSRefDataRequestImpl implements GetMatterStatusesRequest {
    private String operationPath = "api/v1/rdcds/matterstatuses";
    
    public String getOperationPath() {
        return operationPath;
    }

    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }
}

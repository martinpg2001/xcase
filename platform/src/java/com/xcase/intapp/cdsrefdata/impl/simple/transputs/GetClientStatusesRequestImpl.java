package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesRequest;

public class GetClientStatusesRequestImpl extends ListableRefDataRequestImpl implements GetClientStatusesRequest {
    private String operationPath = "api/v1/rdcds/clientstatuses";
    
    public String getOperationPath() {
        return operationPath;
    }

    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }
}

package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import com.xcase.intapp.cdsrefdata.transputs.CreateMatterStatusRequest;

public class CreateMatterStatusRequestImpl extends CDSRefDataRequestImpl implements CreateMatterStatusRequest {
    private String operationPath = "api/v1/rdcds/matterstatuses/_bulk";
    
    public String getOperationPath() {
        return operationPath;
    }

    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }
}

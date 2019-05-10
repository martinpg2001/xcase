package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.GetClientsModifiedSinceDateRequest;

public class GetClientsModifiedSinceDateRequestImpl extends CDSCMRequestImpl implements GetClientsModifiedSinceDateRequest {
    private String operationPath = "api/v1/clients/updates";
    private String since;
    private int successResponseCode = 200;
    
    public int getSuccessResponseCode() {
        return this.successResponseCode;
    }

    public void setSuccessResponseCode(int successResponseCode) {
        this.successResponseCode = successResponseCode;
    }
    
    @Override
    public String getSince() {
        return since;
    }
    
    @Override
    public String getOperationPath() {
        return operationPath;
    }

    @Override
    public void setSince(String since) {
        this.since = since;
    }

}

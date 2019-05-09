package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.GetClientsModifiedSinceDateRequest;

public class GetClientsModifiedSinceDateRequestImpl extends CDSCMRequestImpl implements GetClientsModifiedSinceDateRequest {
    private String operationPath = "api/v1/clients/updates";
    private String since;
    
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

package com.xcase.intapp.cdscm.transputs;

public interface GetClientsModifiedSinceDateRequest extends ListableCDSCMRequest {
    String getSince();
    
    int getSuccessResponseCode();
    
    void setSince(String since);

    String getOperationPath();

}

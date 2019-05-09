package com.xcase.intapp.cdscm.transputs;

public interface GetClientsModifiedSinceDateRequest extends CDSCMRequest {
    String getSince();
    
    void setSince(String since);

    String getOperationPath();

}

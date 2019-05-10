package com.xcase.intapp.cdscm.transputs;

public interface GetMatterRequest extends CDSCMRequest {

	String getClientId();

	String getMatterId();

    String getMatterKey();
    
    String getOperationPath();
    
    int getSuccessResponseCode();
    
    void setClientId(String clientId);

    void setMatterId(String matterId);

    void setMatterKey(String matterKey);

}

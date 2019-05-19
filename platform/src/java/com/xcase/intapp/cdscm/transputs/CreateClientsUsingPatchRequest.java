package com.xcase.intapp.cdscm.transputs;

public interface CreateClientsUsingPatchRequest extends CDSCMRequest {

    void setClients(String[] clientsArray);

    String[] getClients();

    String getOperationPath();

    int getSuccessResponseCode();

}

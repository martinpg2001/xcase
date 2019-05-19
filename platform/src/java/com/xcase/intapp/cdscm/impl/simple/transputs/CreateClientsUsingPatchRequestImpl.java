package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.CreateClientsUsingPatchRequest;

public class CreateClientsUsingPatchRequestImpl extends CDSCMRequestImpl implements CreateClientsUsingPatchRequest {
    private String[] clientsArray;
    private String operationPath = "api/v1/clients";
    private int successResponseCode = 207;
    
    @Override
    public void setClients(String[] clientsArray) {
        this.clientsArray = clientsArray;
    }

    @Override
    public String[] getClients() {
        return clientsArray;
    }

    @Override
    public String getOperationPath() {
        return operationPath;
    }

    @Override
    public int getSuccessResponseCode() {
        return successResponseCode;
    }

}

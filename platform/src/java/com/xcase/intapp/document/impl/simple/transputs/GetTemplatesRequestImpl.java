package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.GetTemplatesRequest;

public class GetTemplatesRequestImpl extends DocumentRequestImpl implements GetTemplatesRequest {
    private String operationPath = "api/v3/templates";
    
    @Override
    public String getOperationPath() {
        return operationPath;
    }

}

package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.HeadTemplatesRequest;

public class HeadTemplatesRequestImpl extends DocumentRequestImpl implements HeadTemplatesRequest {
    private String operationPath = "api/v3/templates";
    
    @Override
    public String getOperationPath() {
        return operationPath;
    }

}

package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.GetTemplateFileRequest;

public class GetTemplateFileRequestImpl extends DocumentRequestImpl implements GetTemplateFileRequest {
    private String id;
    private String operationPath = "api/v3/templates/{templateId}/file";
    
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getOperationPath() {
        return operationPath;
    }

    @Override
    public String getId() {
        return id;
    }

}

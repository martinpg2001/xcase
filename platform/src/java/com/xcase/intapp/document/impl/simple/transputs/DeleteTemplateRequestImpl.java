package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.DeleteTemplateRequest;

public class DeleteTemplateRequestImpl extends DocumentRequestImpl implements DeleteTemplateRequest {
    private String id;
    private String operationPath = "api/v3/templates/{templateId}";
    
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

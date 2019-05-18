package com.xcase.intapp.document.transputs;

public interface DeleteTemplateRequest extends DocumentRequest {

	void setId(String id);

	String getOperationPath();

	String getId();

}

package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.GetCategoriesRequest;

public class GetCategoriesRequestImpl extends DocumentRequestImpl implements GetCategoriesRequest {
    private String operationPath = "api/v2/categories";
    
	@Override
	public String getOperationPath() {
		return operationPath;
	}

}

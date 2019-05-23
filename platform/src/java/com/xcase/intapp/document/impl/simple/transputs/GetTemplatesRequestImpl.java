package com.xcase.intapp.document.impl.simple.transputs;

import com.xcase.intapp.document.transputs.GetTemplatesRequest;

public class GetTemplatesRequestImpl extends DocumentRequestImpl implements GetTemplatesRequest {
    private String operationPath = "api/v3/templates";
    private String sortBy;
    private String sortOrder;
    
    @Override
    public String getOperationPath() {
        return operationPath;
    }

    @Override
    public String getSortBy() {
        return sortBy;
    }

    @Override
    public String getSortOrder() {
        return sortOrder;
    }
    
    @Override
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

}

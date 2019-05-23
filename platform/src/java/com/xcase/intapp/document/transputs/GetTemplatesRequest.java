package com.xcase.intapp.document.transputs;

public interface GetTemplatesRequest extends DocumentRequest {

    String getOperationPath();

    String getSortBy();
    
    String getSortOrder();

    void setSortOrder(String sortOrder);

    void setSortBy(String sortBy);
}

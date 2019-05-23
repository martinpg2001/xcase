package com.xcase.intapp.document.transputs;

public interface ListableDocumentRequest extends DocumentRequest {
    int getLimit();
    
    int getSkip();
    
    void setLimit(int limit);
    
    void setSkip(int skip);
}

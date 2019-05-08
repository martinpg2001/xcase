package com.xcase.intapp.advanced.impl.simple.transputs;

import com.xcase.intapp.advanced.transputs.GetSwaggerDocumentResponse;

public class GetSwaggerDocumentResponseImpl extends AdvancedResponseImpl implements GetSwaggerDocumentResponse {
    private String swaggerDocument;
    
    @Override
    public String getSwaggerDocument() {
        return swaggerDocument;
    }

    @Override
    public void setSwaggerDocument(String swaggerDocument) {
        this.swaggerDocument = swaggerDocument;
    }

}

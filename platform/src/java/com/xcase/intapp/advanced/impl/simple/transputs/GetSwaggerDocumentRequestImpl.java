package com.xcase.intapp.advanced.impl.simple.transputs;

import com.xcase.intapp.advanced.transputs.GetSwaggerDocumentRequest;

public class GetSwaggerDocumentRequestImpl extends AdvancedRequestImpl implements GetSwaggerDocumentRequest {
    private String swaggerAPIURL;
    
    @Override
    public String getSwaggerAPIURL() {
        return swaggerAPIURL;
    }

    @Override
    public void setSwaggerAPIURL(String swaggerAPIURL) {
        this.swaggerAPIURL = swaggerAPIURL;
    }

}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.google.gson.JsonElement;
import com.xcase.common.impl.simple.transputs.RestResponseImpl;
import com.xcase.salesforce.transputs.SalesforceResponse;
import org.apache.http.StatusLine;

/**
 *
 * @author martin
 */
public class SalesforceResponseImpl extends RestResponseImpl implements SalesforceResponse {
    private JsonElement jsonElement;
    
    @Override
    public JsonElement getJsonElement() {
        return jsonElement;
    }

    @Override
    public void setJsonElement(JsonElement jsonElement) {
        this.jsonElement = jsonElement;        
    }
}

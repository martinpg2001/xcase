/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

import com.google.gson.JsonElement;
import com.xcase.common.transputs.RestResponse;
import org.apache.http.StatusLine;

/**
 *
 * @author martin
 */
public interface SalesforceResponse extends RestResponse {
    
    public JsonElement getJsonElement();

    public void setJsonElement(JsonElement jsonElement);
}

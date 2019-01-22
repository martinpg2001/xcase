/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.ExportTagsRequest;

/**
 * @author martin
 *
 */
public class ExportTagsRequestImpl extends BoxRequestImpl implements
        ExportTagsRequest {

    /**
     * auth token.
     */
    private String authToken;

    /**
     * @return the authToken
     */
    public String getAuthToken() {
        return this.authToken;
    }

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_EXPORT_TAGS;
    }
}

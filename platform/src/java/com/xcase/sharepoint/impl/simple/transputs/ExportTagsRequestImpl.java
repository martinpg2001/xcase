/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.transputs.ExportTagsRequest;

/**
 * @author martin
 *
 */
public class ExportTagsRequestImpl extends SharepointRequestImpl implements
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
        return SharepointConstant.ACTION_NAME_EXPORT_TAGS;
    }
}

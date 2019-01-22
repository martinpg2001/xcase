/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.GetTermRequest;

/**
 *
 * @author martin
 */
public class GetTermRequestImpl extends OpenRequestImpl implements GetTermRequest {

    private String clientID;
    private String matterID;
    private String definitionName;

    public String getClientId() {
        return this.clientID;
    }

    public void setClientId(String clientID) {
        this.clientID = clientID;
    }

    public String getMatterId() {
        return this.matterID;
    }

    public void setMatterId(String matterID) {
        this.matterID = matterID;
    }

    public String getDefinitionName() {
        return this.definitionName;
    }

    public void setDefinitionName(String definitionName) {
        this.definitionName = definitionName;
    }
}

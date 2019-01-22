/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.impl.simple.core.UpdateMatterData;
import com.xcase.open.transputs.UpdateMatterRequest;

/**
 *
 * @author martin
 */
public class UpdateMatterRequestImpl extends OpenRequestImpl implements UpdateMatterRequest {

    private String clientId;
    private String matterId;
    private UpdateMatterData updateMatterData;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMatterId() {
        return this.matterId;
    }

    public void setMatterId(String matterId) {
        this.matterId = matterId;
    }

    public UpdateMatterData getUpdateMatterData() {
        return this.updateMatterData;
    }

    public void setUpdateMatterData(UpdateMatterData updateMatterData) {
        this.updateMatterData = updateMatterData;
    }

}

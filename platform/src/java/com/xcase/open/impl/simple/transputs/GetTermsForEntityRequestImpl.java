/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.GetTermsForEntityRequest;

/**
 *
 * @author martin
 */
public class GetTermsForEntityRequestImpl extends OpenRequestImpl implements GetTermsForEntityRequest {

    private String categoryName;
    private String definitionName;
    private String clientId;
    private String matterId;

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

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDefinitionName() {
        return this.definitionName;
    }

    public void setDefinitionName(String definitionName) {
        this.definitionName = definitionName;
    }
}

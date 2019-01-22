/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.transputs;

/**
 *
 * @author martin
 */
public interface GetTermsForEntityRequest {

    String getClientId();

    void setClientId(String clientId);

    String getMatterId();

    void setMatterId(String matterId);

    String getCategoryName();

    void setCategoryName(String categoryName);

    String getDefinitionName();

    void setDefinitionName(String definitionName);
}

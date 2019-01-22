/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.CreateDatasourceResponse;

/**
 *
 * @author martin
 */
public class CreateDatasourceResponseImpl extends IntegrateResponseImpl implements CreateDatasourceResponse {
    private Integer datasourceId;
    private String errorMessage;
    
    public Integer getDatasourceId() {
        return this.datasourceId;
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }    
    
    public void setDatasourceId(Integer datasourceId) {
        this.datasourceId = datasourceId;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    } 
}

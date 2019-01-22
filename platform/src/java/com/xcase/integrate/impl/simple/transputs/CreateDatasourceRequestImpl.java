/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.CreateDatasourceRequest;

/**
 *
 * @author martin
 */
public class CreateDatasourceRequestImpl extends IntegrateRequestImpl implements CreateDatasourceRequest {
    private String datasource;
    
    public String getDatasource() {
        return this.datasource;
    }
    
    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }   
}

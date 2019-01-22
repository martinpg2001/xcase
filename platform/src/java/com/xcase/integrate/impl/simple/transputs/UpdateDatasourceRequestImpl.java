/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.UpdateDatasourceRequest;

/**
 *
 * @author martin
 */
public class UpdateDatasourceRequestImpl extends IntegrateRequestImpl implements UpdateDatasourceRequest {
    private String datasource;
    private Integer datasourceId;
    
    public String getDatasource() {
        return this.datasource;
    }
    
    public Integer getDatasourceId() {
        return this.datasourceId;
    }
    
    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
    
    public void setDatasourceId(Integer datasourceId) {
        this.datasourceId = datasourceId;
    } 
}

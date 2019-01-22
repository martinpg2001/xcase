/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.DeleteDatasourceRequest;

/**
 *
 * @author martinpg
 */
public class DeleteDatasourceRequestImpl extends IntegrateRequestImpl implements DeleteDatasourceRequest {
    private Integer datasourceId;

    public Integer getDatasourceId() {
        return this.datasourceId;
    }

    public void setDatasourceId(Integer datasourceId) {
        this.datasourceId = datasourceId;
    }    
}

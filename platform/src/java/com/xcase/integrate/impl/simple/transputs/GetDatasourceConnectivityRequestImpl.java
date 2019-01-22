/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.GetDatasourceConnectivityRequest;

/**
 *
 * @author Administrator
 */
public class GetDatasourceConnectivityRequestImpl extends IntegrateRequestImpl implements GetDatasourceConnectivityRequest {

    private Integer datasourceId;

    public Integer getDatasourceId() {
        return this.datasourceId;
    }

    public void setDatasourceId(Integer datasourceId) {
        this.datasourceId = datasourceId;
    }
}

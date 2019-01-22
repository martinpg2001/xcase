/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.GetDatasourceRequest;

/**
 *
 * @author martin
 */
public class GetDatasourceRequestImpl extends IntegrateRequestImpl implements GetDatasourceRequest {

    private Integer datasourceId;
    private String datasourceType;

    public Integer getDatasourceId() {
        return this.datasourceId;
    }

    public void setDatasourceId(Integer datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getDatasourceType() {
        return this.datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }
}

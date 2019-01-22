/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martin
 */
public interface GetDatasourceConnectivityRequest extends IntegrateRequest {

    public Integer getDatasourceId();

    public void setDatasourceId(Integer datasourceId);
}

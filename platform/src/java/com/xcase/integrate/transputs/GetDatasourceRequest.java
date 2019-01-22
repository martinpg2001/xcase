/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martin
 */
public interface GetDatasourceRequest extends IntegrateRequest {

    public Integer getDatasourceId();

    public void setDatasourceId(Integer datasourceId);

    public String getDatasourceType();

    public void setDatasourceType(String datasourceType);
}

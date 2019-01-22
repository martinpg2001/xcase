/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martin
 */
public interface UpdateDatasourceRequest extends IntegrateRequest {
    String getDatasource();
    Integer getDatasourceId();
    void setDatasource(String datasource);
    void setDatasourceId(Integer datasourceId);
}

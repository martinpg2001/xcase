/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martin
 */
public interface CreateDatasourceRequest extends IntegrateRequest {
    String getDatasource();
    void setDatasource(String datasource);
}

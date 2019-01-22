/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martin
 */
public interface CreateDatasourceResponse extends IntegrateResponse {
    Integer getDatasourceId();
    String getErrorMessage();
    void setDatasourceId(Integer datasourceId);
    void setErrorMessage(String errorMessage);
}

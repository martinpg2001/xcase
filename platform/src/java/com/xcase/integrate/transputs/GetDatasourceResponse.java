/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.IntegrateDatasource;

/**
 *
 * @author martin
 */
public interface GetDatasourceResponse extends IntegrateResponse {

    public IntegrateDatasource getDatasource();

    public void setDatasource(IntegrateDatasource datasource);
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.IntegrateDatasource;

/**
 *
 * @author martin
 */
public interface GetAllDatasourcesResponse extends IntegrateResponse {

    public void setDatasources(IntegrateDatasource[] datasources);
}

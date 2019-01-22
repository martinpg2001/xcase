/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.IntegrateDatasource;
import com.xcase.integrate.transputs.GetAllDatasourcesResponse;

/**
 *
 * @author martinpg
 */
public class GetAllDatasourcesResponseImpl extends IntegrateResponseImpl implements GetAllDatasourcesResponse {

    private IntegrateDatasource[] datasources;

    public void setDatasources(IntegrateDatasource[] datasources) {
        this.datasources = datasources;
    }
}

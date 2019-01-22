/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.IntegrateDatasource;
import com.xcase.integrate.transputs.GetDatasourceResponse;

/**
 *
 * @author martin
 */
public class GetDatasourceResponseImpl extends IntegrateResponseImpl implements GetDatasourceResponse {

    private IntegrateDatasource datasource;

    public IntegrateDatasource getDatasource() {
        return this.datasource;
    }

    public void setDatasource(IntegrateDatasource datasource) {
        this.datasource = datasource;
    }
}

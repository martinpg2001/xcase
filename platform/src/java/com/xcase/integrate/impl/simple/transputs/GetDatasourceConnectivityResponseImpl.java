/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.objects.IntegrateDatasourceConnectivity;
import com.xcase.integrate.transputs.GetDatasourceConnectivityResponse;

/**
 *
 * @author martin
 */
public class GetDatasourceConnectivityResponseImpl extends IntegrateResponseImpl implements GetDatasourceConnectivityResponse {

    private IntegrateDatasourceConnectivity datasourceConnectivity;

    public IntegrateDatasourceConnectivity getDatasourceConnectivity() {
        return this.datasourceConnectivity;
    }

    public void setDatasourceConnectivity(IntegrateDatasourceConnectivity datasourceConnectivity) {
        this.datasourceConnectivity = datasourceConnectivity;
    }
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import com.xcase.integrate.objects.IntegrateDatasourceConnectivity;

/**
 *
 * @author martin
 */
public interface GetDatasourceConnectivityResponse extends IntegrateResponse {

    public IntegrateDatasourceConnectivity getDatasourceConnectivity();

    public void setDatasourceConnectivity(IntegrateDatasourceConnectivity datasourceConnectivity);

}

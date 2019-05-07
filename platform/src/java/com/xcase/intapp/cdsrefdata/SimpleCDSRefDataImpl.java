package com.xcase.intapp.cdsrefdata;

import com.xcase.intapp.cdsrefdata.impl.simple.core.CDSRefDataConfigurationManager;
import com.xcase.intapp.cdsrefdata.impl.simple.methods.*;
import com.xcase.intapp.cdsrefdata.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleCDSRefDataImpl implements CDSRefDataExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * configuration manager
     */
    public CDSRefDataConfigurationManager localConfigurationManager = CDSRefDataConfigurationManager.getConfigurationManager();
    
    /**
     * method implementation.
     */
    private GetClientStatusesMethod getClientStatusesMethod = new GetClientStatusesMethod();
    
    /**
     * method implementation.
     */
    private GetMatterStatusesMethod getMatterStatusesMethod = new GetMatterStatusesMethod();

    @Override
    public GetClientStatusesResponse getClientStatuses(GetClientStatusesRequest getClientStatusesRequest) {
        return this.getClientStatusesMethod.getClientStatuses(getClientStatusesRequest);
    }
    
    @Override
    public GetMatterStatusesResponse getMatterStatuses(GetMatterStatusesRequest getMatterStatusesRequest) {
        return this.getMatterStatusesMethod.getMatterStatuses(getMatterStatusesRequest);
    }

}
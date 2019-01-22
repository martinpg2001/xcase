/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.objects;

import com.google.gson.*;
import com.xcase.salesforce.objects.SalesforceAccount;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SalesforceAccountImpl extends SalesforceObjectImpl implements SalesforceAccount {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static SalesforceAccountImpl CreateSalesforceAccountImpl(JsonObject jsonObject) {
        LOGGER.debug("starting CreateSalesforceAccountImpl()");
        Gson gson = new GsonBuilder().create();
        SalesforceAccountImpl salesforceAccountImpl = gson.fromJson(jsonObject, com.xcase.salesforce.impl.simple.objects.SalesforceAccountImpl.class);
        LOGGER.debug("created salesforceAccountImpl object with name " + salesforceAccountImpl.getName());
        return salesforceAccountImpl;
    }

    private String Name;

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.xcase.salesforce.objects.SalesforceRecord;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SalesforceRecordImpl extends SalesforceObjectImpl implements SalesforceRecord {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static SalesforceRecordImpl CreateSalesforceRecordImpl(JsonObject jsonObject) {
        Gson gson = new GsonBuilder().create();
        SalesforceRecordImpl salesforceRecordImpl = gson.fromJson(jsonObject, com.xcase.salesforce.impl.simple.objects.SalesforceRecordImpl.class);
        LOGGER.debug("created salesforceRecordImpl object with name " + salesforceRecordImpl.getName());
        return salesforceRecordImpl;
    }

    private String Name;

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

}

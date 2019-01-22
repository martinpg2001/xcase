/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import java.io.*;
import java.lang.invoke.*;
import java.net.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.message.*;
import org.apache.logging.log4j.*;

/*
/// Web Proxy for TermsApi
 */
public class TermsApiWebProxy extends SwaggerProxy implements ITermsApiWebProxy {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public TermsApiWebProxy(URL baseUrl) {
        super(baseUrl);
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="String">Gets or sets the category name.</param>
    /// <param name="String">Gets or sets the client ID.</param>
    /// <param name="String">Gets or sets the matter ID.</param>
    /// <param name="String">Gets or sets the name.</param>
    /// <param name="Date">Gets or sets the effective start date.</param>
    /// <param name="Date">Gets or sets the effective end date.</param>
    public TermData[] GetTerms(String filter__category, String filter__clientId, String filter__matterId, String filter__name, Date filter__effectiveStartDate, Date filter__effectiveEndDate) {
        LOGGER.debug("starting GetTerms()");
        String url = "api/terms/v1";

        url = baseUrl + url;
        if (filter__category != null) {
            url = AppendQuery(url, "filter.category", filter__category.toString());
        }
        if (filter__clientId != null) {
            url = AppendQuery(url, "filter.clientId", filter__clientId.toString());
        }
        if (filter__matterId != null) {
            url = AppendQuery(url, "filter.matterId", filter__matterId.toString());
        }
        if (filter__name != null) {
            url = AppendQuery(url, "filter.name", filter__name.toString());
        }
        if (filter__effectiveStartDate != null) {
            url = AppendQuery(url, "filter.effectiveStartDate", filter__effectiveStartDate.toString());
        }
        if (filter__effectiveEndDate != null) {
            url = AppendQuery(url, "filter.effectiveEndDate", filter__effectiveEndDate.toString());
        }

        LOGGER.debug("url is " + url);
        try (CommonHTTPManager apiClient = buildHttpClient()) {
            Header acceptHeader = new BasicHeader("Accept", "application/json");
            Header bearerHeader = new BasicHeader("Authorization", "Bearer " + accessToken);
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            Header[] headers = new Header[]{acceptHeader, bearerHeader, contentTypeHeader};
            JsonElement response = apiClient.doJsonGet(url, headers, null);
            Gson responseGson = new Gson();
            TermData[] entity = responseGson.fromJson(response, TermData[].class);
            return entity;
        } catch (IOException ioe) {
            LOGGER.warn("ioexception invoking method: " + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }

}

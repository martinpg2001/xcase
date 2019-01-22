/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.SystemMemoryHistory;
import com.xcase.integrate.objects.SystemMemoryUsage;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetSystemMemoryHistoryRequest;
import com.xcase.integrate.transputs.GetSystemMemoryHistoryResponse;
import java.io.StringReader;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martinpg
 */
public class GetSystemMemoryHistoryMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetSystemMemoryHistoryResponse getSystemMemoryHistory(GetSystemMemoryHistoryRequest request) {
        LOGGER.debug("starting getSystemMemoryHistory()");
        GetSystemMemoryHistoryResponse response = IntegrateResponseFactory.createGetSystemMemoryHistoryResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "systems";
            String memoryHistory = "memory/history";
            LOGGER.debug("memoryHistory is " + memoryHistory);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + memoryHistory;
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
//            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "GET");
//            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                    SystemMemoryHistory systemMemoryHistory = gson.fromJson(responseEntityJsonObject, SystemMemoryHistory.class);
                    LOGGER.debug("created systemMemoryHistory");
                    JsonArray memoryUsagesArray = responseEntityJsonObject.getAsJsonArray("memory_usages");
                    Iterator<JsonElement> memoryUsagesArrayIterator = memoryUsagesArray.iterator();
                    List<SystemMemoryUsage> systemMemoryUsageList = new ArrayList<SystemMemoryUsage>();
                    while (memoryUsagesArrayIterator.hasNext()) {
                        SystemMemoryUsage systemMemoryUsage = gson.fromJson(memoryUsagesArrayIterator.next(), SystemMemoryUsage.class);
                        systemMemoryUsageList.add(systemMemoryUsage);
                    }

                    systemMemoryHistory.setSystemMemoryUsageList(systemMemoryUsageList);
                    response.setSystemMemoryHistory(systemMemoryHistory);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(SystemMemoryHistory.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    SystemMemoryHistory systemMemoryHistory = (SystemMemoryHistory) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created systemMemoryHistory");
                    response.setSystemMemoryHistory(systemMemoryHistory);
                } else {
                    LOGGER.warn("unexpected API request format: " + this.apiRequestFormat);
                }
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }
}

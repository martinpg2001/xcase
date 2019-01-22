/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.SystemCPULoadAverageHistory;
import com.xcase.integrate.objects.CPULoadAverage;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetSystemCPULoadAverageHistoryRequest;
import com.xcase.integrate.transputs.GetSystemCPULoadAverageHistoryResponse;
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
public class GetSystemCPULoadAverageHistoryMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetSystemCPULoadAverageHistoryResponse getSystemCPULoadAverageHistory(GetSystemCPULoadAverageHistoryRequest request) {
        LOGGER.debug("starting getSystemCPULoadAverageHistory()");
        GetSystemCPULoadAverageHistoryResponse response = IntegrateResponseFactory.createGetSystemCPULoadAverageHistoryResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "systems";
            String cpuLoadAvgHistory = "cpuloadavg/history";
            LOGGER.debug("cpuLoadAvgHistory is " + cpuLoadAvgHistory);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + cpuLoadAvgHistory;
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
                    SystemCPULoadAverageHistory systemCPULoadAverageHistory = gson.fromJson(responseEntityJsonObject, SystemCPULoadAverageHistory.class);
                    LOGGER.debug("created systemCPULoadAverageHistory");
                    JsonArray cpuLoadAveragesArray = responseEntityJsonObject.getAsJsonArray("cpu_load_avgs");
                    Iterator<JsonElement> cpuLoadAveragesArrayIterator = cpuLoadAveragesArray.iterator();
                    List<CPULoadAverage> cpuLoadAverageList = new ArrayList<CPULoadAverage>();
                    while (cpuLoadAveragesArrayIterator.hasNext()) {
                        CPULoadAverage cpuLoadAverage = gson.fromJson(cpuLoadAveragesArrayIterator.next(), CPULoadAverage.class);
                        cpuLoadAverageList.add(cpuLoadAverage);
                    }

                    systemCPULoadAverageHistory.setAgentActivityList(cpuLoadAverageList);
                    response.setSystemCPULoadAverageHistory(systemCPULoadAverageHistory);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(SystemCPULoadAverageHistory.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    SystemCPULoadAverageHistory systemCPULoadAverageHistory = (SystemCPULoadAverageHistory) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created systemCPULoadAverageHistory");
                    response.setSystemCPULoadAverageHistory(systemCPULoadAverageHistory);
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

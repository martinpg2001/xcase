/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.ServiceStatus;
import com.xcase.integrate.objects.ServiceStatuses;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetServiceStatusRequest;
import com.xcase.integrate.transputs.GetServiceStatusResponse;
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
public class GetServiceStatusMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetServiceStatusResponse getServiceStatus(GetServiceStatusRequest request) {
        LOGGER.debug("starting getServiceStatus()");
        GetServiceStatusResponse response = IntegrateResponseFactory.createGetServiceStatusResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "appliance";
            String servicesStatus = "services/status";
            LOGGER.debug("servicesStatus is " + servicesStatus);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + servicesStatus;
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
                    ServiceStatuses serviceStatuses = gson.fromJson(responseEntityJsonObject, ServiceStatuses.class);
                    LOGGER.debug("created serviceStatuses");
                    JsonArray serviceStatusesArray = responseEntityJsonObject.getAsJsonArray("service_statuses");
                    Iterator<JsonElement> serviceStatusesArrayIterator = serviceStatusesArray.iterator();
                    List<ServiceStatus> serviceStatusList = new ArrayList<ServiceStatus>();
                    while (serviceStatusesArrayIterator.hasNext()) {
                        ServiceStatus ruleProcessorStatus = gson.fromJson(serviceStatusesArrayIterator.next(), ServiceStatus.class);
                        serviceStatusList.add(ruleProcessorStatus);
                    }

                    serviceStatuses.setServiceStatusList(serviceStatusList);
                    response.setServiceStatuses(serviceStatuses);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(ServiceStatuses.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    ServiceStatuses serviceStatuses = (ServiceStatuses) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created serviceStatuses");
                    response.setServiceStatuses(serviceStatuses);
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

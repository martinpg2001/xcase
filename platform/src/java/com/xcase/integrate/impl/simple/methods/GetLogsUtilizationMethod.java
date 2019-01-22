/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.transputs.GetLogsUtilizationResponse;
import com.xcase.integrate.transputs.GetLogsUtilizationRequest;
import com.xcase.integrate.objects.JsonIncreasedLogEntries7Days;
import com.xcase.integrate.objects.IncreasedLogEntries7Days;
import com.xcase.integrate.objects.IncreasedEntries;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import java.io.StringReader;
import java.lang.invoke.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetLogsUtilizationMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetLogsUtilizationResponse getLogsUtilization(GetLogsUtilizationRequest request) {
        LOGGER.debug("starting getLogsUtilization()");
        GetLogsUtilizationResponse response = IntegrateResponseFactory.createGetLogsUtilizationResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "logs";
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + IntegrateConstant.UTILIZATION;
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
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
                    JsonObject logUtilizationJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                    LOGGER.debug("got response object");
                    JsonIncreasedLogEntries7Days increasedLogEntries7Days = gson.fromJson(logUtilizationJsonObject, JsonIncreasedLogEntries7Days.class);
                    LOGGER.debug("created increasedLogEntries7Days");
                    response.setJsonIncreasedLogEntries7Days(increasedLogEntries7Days);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(IncreasedLogEntries7Days.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    IncreasedLogEntries7Days increasedLogEntries7Days = (IncreasedLogEntries7Days) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created increasedLogEntries7Days");
                    if (increasedLogEntries7Days.api_log != null) {
                        LOGGER.debug("increasedLogEntries7Days api_log is not null");
                        if (increasedLogEntries7Days.api_log.getIncreasedEntries() != null) {
                            LOGGER.debug("increasedLogEntries7Days api_log getIncreasedEntries is not null");
                            for (IncreasedEntries increasedEntries : increasedLogEntries7Days.api_log.getIncreasedEntries()) {
                                LOGGER.debug("increasedEntries value is " + increasedEntries.value);
                            }
                        } else {
                            LOGGER.debug("increasedLogEntries7Days event_log getIncreasedEntries is null");
                        }
                    }

                    if (increasedLogEntries7Days.event_log != null) {
                        LOGGER.debug("increasedLogEntries7Days event_log is not null");
                        if (increasedLogEntries7Days.event_log.getIncreasedEntries() != null) {
                            LOGGER.debug("increasedLogEntries7Days event_log getIncreasedEntries is not null");
                            for (IncreasedEntries increasedEntries : increasedLogEntries7Days.event_log.getIncreasedEntries()) {
                                LOGGER.debug("increasedEntries value is " + increasedEntries.value);
                            }
                        } else {
                            LOGGER.debug("increasedLogEntries7Days event_log getIncreasedEntries is null");
                        }
                    }

                    if (increasedLogEntries7Days.rule_log != null) {
                        LOGGER.debug("increasedLogEntries7Days rule_log is not null");
                        if (increasedLogEntries7Days.rule_log.getIncreasedEntries() != null) {
                            LOGGER.debug("increasedLogEntries7Days rule_log getIncreasedEntries is not null");
                            for (IncreasedEntries increasedEntries : increasedLogEntries7Days.rule_log.getIncreasedEntries()) {
                                LOGGER.debug("increasedEntries value is " + increasedEntries.value);
                            }
                        } else {
                            LOGGER.debug("increasedLogEntries7Days rule_log getIncreasedEntries is null");
                        }
                    }

                    if (increasedLogEntries7Days.security_log != null) {
                        LOGGER.debug("increasedLogEntries7Days security_log is not null");
                        if (increasedLogEntries7Days.security_log.getIncreasedEntries() != null) {
                            LOGGER.debug("increasedLogEntries7Days security_log getIncreasedEntries is not null");
                            for (IncreasedEntries increasedEntries : increasedLogEntries7Days.security_log.getIncreasedEntries()) {
                                LOGGER.debug("increasedEntries value is " + increasedEntries.value);
                            }
                        } else {
                            LOGGER.debug("increasedLogEntries7Days security_log getIncreasedEntries is null");
                        }
                    }

                    if (increasedLogEntries7Days.systems_log != null) {
                        LOGGER.debug("increasedLogEntries7Days systems_log is not null");
                        if (increasedLogEntries7Days.systems_log.getIncreasedEntries() != null) {
                            LOGGER.debug("increasedLogEntries7Days systems_log getIncreasedEntries is not null");
                            for (IncreasedEntries increasedEntries : increasedLogEntries7Days.systems_log.getIncreasedEntries()) {
                                LOGGER.debug("increasedEntries value is " + increasedEntries.value);
                            }
                        } else {
                            LOGGER.debug("increasedLogEntries7Days systems_log getIncreasedEntries is null");
                        }
                    }

                    if (increasedLogEntries7Days.webservice_log != null) {
                        LOGGER.debug("increasedLogEntries7Days webservice_log is not null");
                        if (increasedLogEntries7Days.webservice_log.getIncreasedEntries() != null) {
                            LOGGER.debug("increasedLogEntries7Days webservice_log getIncreasedEntries is not null");
                            for (IncreasedEntries increasedEntries : increasedLogEntries7Days.webservice_log.getIncreasedEntries()) {
                                LOGGER.debug("increasedEntries value is " + increasedEntries.value);
                            }
                        } else {
                            LOGGER.debug("increasedLogEntries7Days webservice_log getIncreasedEntries is null");
                        }
                    }

                    response.setIncreasedLogEntries7Days(increasedLogEntries7Days);
                } else {
                    LOGGER.warn("unexpected API request format: " + this.apiRequestFormat);
                    response.setMessage("Unexpected API request format: " + this.apiRequestFormat);
                    response.setStatus("FAIL");
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

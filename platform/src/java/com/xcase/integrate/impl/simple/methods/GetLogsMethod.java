/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.IntegrateBulkLogEntry;
import com.xcase.integrate.objects.IntegrateLogList;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetLogsRequest;
import com.xcase.integrate.transputs.GetLogsResponse;
import java.io.StringReader;
import java.lang.Thread;
import java.lang.invoke.*;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetLogsMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetLogsResponse getLogs(GetLogsRequest request) {
        LOGGER.debug("starting getLogs()");
        GetLogsResponse response = IntegrateResponseFactory.createGetLogsResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "logs";
            String logType = request.getLogType();
            LOGGER.debug("logType is " + logType);
            Date afterDate = request.getAfterDate();
            LOGGER.debug("afterDate is " + afterDate);
            Date endingAtDate = request.getEndingAtDate();
            LOGGER.debug("endingAtDate is " + endingAtDate);
            Integer entriesPerPage = request.getEntriesPerPage();
            LOGGER.debug("entriesPerPage is " + entriesPerPage);
            String nextPageStartsAt = request.getNextPageStartsAt();
            LOGGER.debug("nextPageStartsAt is " + nextPageStartsAt);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + logType + CommonConstant.QUESTION_MARK_STRING + "entriesPerPage" + CommonConstant.EQUALS_SIGN_STRING + entriesPerPage;
            if (afterDate != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "after" + CommonConstant.EQUALS_SIGN_STRING + afterDate;
            }

            if (endingAtDate != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "endingAt" + CommonConstant.EQUALS_SIGN_STRING + endingAtDate;
            }

            if (nextPageStartsAt != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "startingId" + CommonConstant.EQUALS_SIGN_STRING + nextPageStartsAt;
            }
            
            if ("api".equals(logType)) {
                String requestType = request.getRequestType();
                LOGGER.debug("requestType is " + requestType);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "request_type=" + requestType;
                String responseCode = request.getResponseCode();
                LOGGER.debug("responseCode is " + responseCode);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "response_code=" + responseCode;
                String sourceIp = request.getSourceIp();
                LOGGER.debug("sourceIp is " + sourceIp);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "source_ip=" + sourceIp;
                String username = request.getUsername();
                LOGGER.debug("username is " + username);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "username=" + username;
            }

            if ("event".equals(logType)) {
                Integer eventId = request.getEventId();
                LOGGER.debug("eventId is " + eventId);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "event_id=" + eventId;
                String eventType = request.getEventType();
                LOGGER.debug("eventType is " + eventType);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "event_type=" + eventType;
            }
            
            if ("rule".equals(logType)) {
                Integer ruleId = request.getRuleId();
                LOGGER.debug("ruleId is " + ruleId);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "rule_id=" + ruleId;
                String statusString = request.getStatusString();
                LOGGER.debug("statusString is " + statusString);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + statusString;
            }
            
            if ("security".equals(logType)) {
                String securityEventType = request.getSecurityEventType();
                LOGGER.debug("securityEventType is " + securityEventType);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "event_type=" + securityEventType;
            }
            
            if ("system".equals(logType)) {
                String messageType = request.getMessageType();
                LOGGER.debug("messageType is " + messageType);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "message_type=" + messageType;
                String systemComponent = request.getSystemComponent();
                LOGGER.debug("systemComponent is " + systemComponent);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + "system_component=" + systemComponent;
            }
            
            if ("webservice".equals(logType)) {
                String statusString = request.getStatusString();
                LOGGER.debug("statusString is " + statusString);
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + statusString;
            }

            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            /* Sleep for 0.1 seconds to slow looping through logs */
            Thread.sleep(100);
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    JsonObject logEntriesJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                    LOGGER.debug("got response object");
                    JsonArray logEntriesJsonArray = logEntriesJsonObject.getAsJsonArray("log_entries");
                    LOGGER.debug("got logEntries JsonArray");
                    IntegrateBulkLogEntry[] logEntries = gson.fromJson(logEntriesJsonArray, IntegrateBulkLogEntry[].class);
                    LOGGER.debug("got logEntries " + logEntries.length);
                    response.setLogEntries(logEntries);
                    JsonPrimitive nextPageStartsAtJsonPrimitive = logEntriesJsonObject.getAsJsonPrimitive("next_page_starts_at");
                    if (nextPageStartsAtJsonPrimitive != null) {
                        nextPageStartsAt = nextPageStartsAtJsonPrimitive.getAsString();
                        LOGGER.debug("nextPageStartsAt is " + nextPageStartsAt);
                        response.setNextPageStartsAt(nextPageStartsAt);
                    }
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(IntegrateLogList.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    IntegrateLogList integrateLogList = (IntegrateLogList) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created logList");
                    IntegrateBulkLogEntry[] logEntries = integrateLogList.getLogEntries().toArray(new IntegrateBulkLogEntry[0]);
                    LOGGER.debug("created logEntries");
                    response.setLogEntries(logEntries);
                    nextPageStartsAt = integrateLogList.getNextPageStartsAt();
                    LOGGER.debug("nextPageStartsAt is " + nextPageStartsAt);
                    response.setNextPageStartsAt(nextPageStartsAt);
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

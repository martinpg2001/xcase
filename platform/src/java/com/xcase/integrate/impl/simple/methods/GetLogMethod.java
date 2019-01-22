/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.IntegrateRuleLogEntry;
import com.xcase.integrate.objects.IntegrateSystemLogEntry;
import com.xcase.integrate.objects.IntegrateAPILogEntry;
import com.xcase.integrate.objects.IntegrateSecurityLogEntry;
import com.xcase.integrate.objects.IntegrateWebServiceLogEntry;
import com.xcase.integrate.objects.IntegrateEventLogEntry;
import com.xcase.common.utils.XMLUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetLogRequest;
import com.xcase.integrate.transputs.GetLogResponse;
import java.io.*;
import java.lang.invoke.*;
import java.util.zip.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.*;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.*;

/**
 *
 * @author martin
 */
public class GetLogMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetLogResponse getLog(GetLogRequest request) {
        LOGGER.debug("starting getLog()");
        GetLogResponse response = IntegrateResponseFactory.createGetLogResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "logs";
            String logType = request.getLogType();
            LOGGER.debug("logType is " + logType);
            int logId = request.getLogId();
            LOGGER.debug("logId is " + logId);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + logType + CommonConstant.SLASH_STRING + logId;
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader(request.getAccept());
            LOGGER.debug("created Accept header " + acceptHeader.toString());
            Header contentTypeHeader = createContentTypeHeader();
//            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "GET");
//            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                Document document;
                JAXBContext jaxbContext;
                Unmarshaller jaxbUnmarshaller;
                switch (logType) {
                    case "api":
                        LOGGER.debug("processing api log type");
                        document = getDocumentFromCommonHttpResponse(commonHttpResponse);
                        LOGGER.debug(XMLUtils.ConvertDocumentoString(document));
                        jaxbContext = JAXBContext.newInstance(IntegrateAPILogEntry.class);
                        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        IntegrateAPILogEntry apiLogEntry = (IntegrateAPILogEntry) jaxbUnmarshaller.unmarshal(document);
                        LOGGER.debug("created apiLogEntry");
                        response.setLogEntry(apiLogEntry);
                        break;
                    case "event":
                        LOGGER.debug("processing event log type");
                        document = getDocumentFromCommonHttpResponse(commonHttpResponse);
                        LOGGER.debug(XMLUtils.ConvertDocumentoString(document));
                        jaxbContext = JAXBContext.newInstance(IntegrateEventLogEntry.class);
                        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        IntegrateEventLogEntry eventLogEntry = (IntegrateEventLogEntry) jaxbUnmarshaller.unmarshal(document);
                        LOGGER.debug("created eventLogEntry");
                        response.setLogEntry(eventLogEntry);
                        break;
                    case "rule":
                        LOGGER.debug("processing rule log type");
                        document = getDocumentFromCommonHttpResponse(commonHttpResponse);
                        LOGGER.debug(XMLUtils.ConvertDocumentoString(document));
                        jaxbContext = JAXBContext.newInstance(IntegrateRuleLogEntry.class);
                        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        IntegrateRuleLogEntry ruleLogEntry = (IntegrateRuleLogEntry) jaxbUnmarshaller.unmarshal(document);
                        LOGGER.debug("created ruleLogEntry");
                        response.setLogEntry(ruleLogEntry);
                        break;
                    case "security":
                        LOGGER.debug("processing security log type");
                        document = getDocumentFromCommonHttpResponse(commonHttpResponse);
                        LOGGER.debug(XMLUtils.ConvertDocumentoString(document));
                        jaxbContext = JAXBContext.newInstance(IntegrateSecurityLogEntry.class);
                        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        IntegrateSecurityLogEntry securityLogEntry = (IntegrateSecurityLogEntry) jaxbUnmarshaller.unmarshal(document);
                        LOGGER.debug("created securityLogEntry");
                        response.setLogEntry(securityLogEntry);
                        break;
                    case "system":
                        LOGGER.debug("processing system log type");
                        document = getDocumentFromCommonHttpResponse(commonHttpResponse);
                        LOGGER.debug(XMLUtils.ConvertDocumentoString(document));
                        jaxbContext = JAXBContext.newInstance(IntegrateSystemLogEntry.class);
                        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        IntegrateSystemLogEntry systemLogEntry = (IntegrateSystemLogEntry) jaxbUnmarshaller.unmarshal(document);
                        LOGGER.debug("created systemLogEntry");
                        response.setLogEntry(systemLogEntry);
                        break;
                    case "webservice":
                        LOGGER.debug("processing webservice log type");
                        document = getDocumentFromCommonHttpResponse(commonHttpResponse);
                        LOGGER.debug(XMLUtils.ConvertDocumentoString(document));
                        jaxbContext = JAXBContext.newInstance(IntegrateWebServiceLogEntry.class);
                        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        IntegrateWebServiceLogEntry webServiceLogEntry = (IntegrateWebServiceLogEntry) jaxbUnmarshaller.unmarshal(document);
                        LOGGER.debug("created webServiceLogEntry");
                        response.setLogEntry(webServiceLogEntry);
                        break;
                    default:
                        LOGGER.warn("invalid log type: " + logType);
                        response.setMessage("Invalid log type: " + logType);
                        response.setStatus("FAIL");
                        break;
                }
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }

    private Document getDocumentFromCommonHttpResponse(CommonHttpResponse commonHttpResponse) throws ParserConfigurationException, SAXException, IOException {
        try {
            byte[] contentByteArray = commonHttpResponse.getContent();
            LOGGER.debug("got byte array");
            InputStream inputStream = new ByteArrayInputStream(contentByteArray);
            LOGGER.debug("created inputStream from contentByteArray");
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            LOGGER.debug("created gzipInputStream from inputStream");
//            InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
//            LOGGER.debug("created inputStreamReader from gzipInputStream");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            LOGGER.debug("created bufferedReader from inputStreamReader");
//            String line ;
//            while ((line = bufferedReader.readLine()) != null) {
//                LOGGER.debug(line);
//            }

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(gzipInputStream);
            return document;
        } catch (ParserConfigurationException pce) {
            LOGGER.warn("exception parsing response: " + pce.getMessage());
            throw pce;
        } catch (SAXException se) {
            LOGGER.warn("exception parsing response: " + se.getMessage());
            throw se;
        } catch (IOException ioe) {
            LOGGER.warn("exception parsing response: " + ioe.getMessage());
            throw ioe;
        }
    }
}

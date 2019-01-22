/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.IntegrateDatasourceList;
import com.xcase.integrate.objects.IntegrateDatasource;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetAllDatasourcesRequest;
import com.xcase.integrate.transputs.GetAllDatasourcesResponse;
import java.io.StringReader;
import java.lang.invoke.*;
import javax.xml.bind.*;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetDatasourcesMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetAllDatasourcesResponse getDatasources(GetAllDatasourcesRequest request) {
        LOGGER.debug("starting getDatasources()");
        GetAllDatasourcesResponse response = IntegrateResponseFactory.createGetAllDatasourcesResponse();
        LOGGER.debug("created getAllDatasourcesResponse");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "datasources";
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object;
            String encryptionKey = request.getEncryptionKey();
            LOGGER.debug("encryptionKey is " + encryptionKey);
            if (encryptionKey != null) {
                endPoint = endPoint + CommonConstant.QUESTION_MARK_STRING + "encryption_key" + CommonConstant.EQUALS_SIGN_STRING + encryptionKey;
            }
            
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
//            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "GET");
//            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {acceptHeader, authorizationHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    JsonArray datasourcesArray = (JsonArray) ConverterUtils.parseStringToJson(responseEntityString);
                    LOGGER.debug("parsed responseEntityString to JsonArray");
                    IntegrateDatasource[] datasources = gson.fromJson(datasourcesArray, IntegrateDatasource[].class);
                    LOGGER.debug("got datasources " + datasources.length);
                    response.setDatasources(datasources);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(IntegrateDatasourceList.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    IntegrateDatasourceList datasourceList = (IntegrateDatasourceList) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created datasourceList");
                    IntegrateDatasource[] datasources = datasourceList.getDatasources().toArray(new IntegrateDatasource[0]);
                    LOGGER.debug("created datasources");
                    response.setDatasources(datasources);
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

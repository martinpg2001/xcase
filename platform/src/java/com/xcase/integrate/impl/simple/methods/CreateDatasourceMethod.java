/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.objects.DatasourceCreationResponse;
import com.xcase.integrate.transputs.CreateDatasourceRequest;
import com.xcase.integrate.transputs.CreateDatasourceResponse;
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
public class CreateDatasourceMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public CreateDatasourceResponse createDatasource(CreateDatasourceRequest request) {
        LOGGER.debug("starting createDatasource()");
        CreateDatasourceResponse response = IntegrateResponseFactory.createCreateDatasourceResponse();
        LOGGER.debug("created createDatasourceResponse");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "datasources";
            String datasource = request.getDatasource();
            LOGGER.debug("datasource is " + datasource);
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
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", endPoint, headers, null, datasource, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 201) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    JsonObject datasourceJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                    DatasourceCreationResponse datasourceCreationResponse = gson.fromJson(datasourceJsonObject, DatasourceCreationResponse.class);
                    Integer datasourceId = Integer.valueOf(datasourceCreationResponse.id);
                    LOGGER.debug("datasourceId is " + datasourceId);
                    response.setDatasourceId(datasourceId);
                    String errorMessage = datasourceCreationResponse.message;
                    LOGGER.debug("errorMessage is " + errorMessage);
                    response.setErrorMessage(errorMessage);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(DatasourceCreationResponse.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    DatasourceCreationResponse datasourceCreationResponse = (DatasourceCreationResponse) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created datasourceCreationResponse");
                    Integer datasourceId = Integer.valueOf(datasourceCreationResponse.id);
                    LOGGER.debug("datasourceId is " + datasourceId);
                    response.setDatasourceId(datasourceId);
                    String errorMessage = datasourceCreationResponse.message;
                    LOGGER.debug("errorMessage is " + errorMessage);
                    response.setErrorMessage(errorMessage);
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

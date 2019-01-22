/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.IntegrateDatabaseDatasource;
import com.xcase.integrate.objects.IntegrateHttpDatasource;
import com.xcase.integrate.objects.IntegrateDatasource;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.UpdateDatasourceRequest;
import com.xcase.integrate.transputs.UpdateDatasourceResponse;
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
public class UpdateDatasourceMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public UpdateDatasourceResponse updateDatasource(UpdateDatasourceRequest request) {
        LOGGER.debug("starting updateDatasource()");
        UpdateDatasourceResponse response = IntegrateResponseFactory.createUpdateDatasourceResponse();
        LOGGER.debug("created updateDatasourceResponse");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "datasources";
            Integer datasourceId = request.getDatasourceId();
            LOGGER.debug("datasourceId is " + datasourceId);
            String datasource = request.getDatasource();
            LOGGER.debug("datasource is " + datasource);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + datasourceId;
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
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("PATCH", endPoint, headers, null, datasource, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 204) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    /* Nothing to do */
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    /* Nothing to do */
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

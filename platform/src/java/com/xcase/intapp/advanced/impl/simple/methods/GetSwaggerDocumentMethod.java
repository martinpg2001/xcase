package com.xcase.intapp.advanced.impl.simple.methods;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.advanced.factories.AdvancedResponseFactory;
import com.xcase.intapp.advanced.transputs.GetSwaggerDocumentRequest;
import com.xcase.intapp.advanced.transputs.GetSwaggerDocumentResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetSwaggerDocumentMethod extends BaseAdvancedMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetSwaggerDocumentResponse getSwaggerDocument(GetSwaggerDocumentRequest request) {
        LOGGER.debug("starting getSwaggerDocument()");
        GetSwaggerDocumentResponse response = AdvancedResponseFactory.createGetSwaggerDocumentResponse();
        LOGGER.debug("created response");
        try {
            String accessToken = request.getAccessToken();
            String swaggerAPIURL = request.getSwaggerAPIURL();
            CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("GET", swaggerAPIURL, null, null, null, null);
            LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                String swaggerDocument = responseEntityString;
                LOGGER.debug("swaggerDocument is " + swaggerDocument);
                response.setSwaggerDocument(swaggerDocument);
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }
}

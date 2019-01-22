package com.xcase.ebay.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.ebay.constant.EBayConstant;
import com.xcase.ebay.factories.EBayResponseFactory;
import com.xcase.ebay.impl.simple.core.EBayConfigurationManager;
import com.xcase.ebay.objects.EBayException;
import com.xcase.ebay.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvokeAdvancedActionMethod extends BaseEBayMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public InvokeAdvancedActionResponse invokeAdvancedAction(InvokeAdvancedActionRequest invokeAdvancedActionRequest) throws Exception {
        LOGGER.debug("starting createApplicationAccessToken()");
        InvokeAdvancedActionResponse invokeAdvancedActionResponse = EBayResponseFactory.createInvokeAdvancedActionResponse();
        LOGGER.debug("created access token response");
        Header authorizationHeader = createAuthorizationHeader(invokeAdvancedActionRequest);
        LOGGER.debug("created Authorization header");
        Header contentTypeHeader = createContentTypeHeader();
        Header[] headers = {authorizationHeader, contentTypeHeader};
        String endpoint = invokeAdvancedActionRequest.getEndpoint();
        LOGGER.debug("endpoint is " + endpoint);
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", endpoint, headers, null, null, null);
        int responseCode = commonHttpResponse.getResponseCode();
        if (responseCode == 200) {
            try {
                JsonElement jsonElement = ConverterUtils.parseStringToJson(commonHttpResponse.getResponseEntityString());;
                if (!jsonElement.isJsonNull()) {
                    JsonObject jsonObject = (JsonObject) jsonElement;
                } else {
                    String status = EBayConstant.STATUS_NOT_LOGGED_IN;
                    invokeAdvancedActionResponse.setStatus(status);
                }
            } catch (Exception e) {
                throw new EBayException("Failed to parse to a document.", e);
            }
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return invokeAdvancedActionResponse;
    }

	private Header createAuthorizationHeader(InvokeAdvancedActionRequest invokeAdvancedActionRequest) {
        String accessToken = invokeAdvancedActionRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        return new BasicHeader("Authorization", "Bearer " + accessToken);
	}
}

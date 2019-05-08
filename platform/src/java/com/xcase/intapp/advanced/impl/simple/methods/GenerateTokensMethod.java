package com.xcase.intapp.advanced.impl.simple.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.advanced.constant.AdvancedConstant;
import com.xcase.intapp.advanced.factories.AdvancedResponseFactory;
import com.xcase.intapp.advanced.impl.simple.core.AdvancedConfigurationManager;
import com.xcase.intapp.advanced.transputs.GenerateTokensRequest;
import com.xcase.intapp.advanced.transputs.GenerateTokensResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenerateTokensMethod extends BaseAdvancedMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GenerateTokensResponse generateTokens(GenerateTokensRequest request) {
        LOGGER.debug("starting generateTokens()");
        GenerateTokensResponse response = AdvancedResponseFactory.createGenerateTokensResponse();
        LOGGER.debug("created response");
        try {
            String clientID = request.getClientId();
            String clientSecret = request.getClientSecret();
            String swaggerAPIURL = request.getSwaggerAPIURL();
            String tokenURL = request.getTokenURL();
            CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
            parameters.add(new BasicNameValuePair("client_id", clientID));
            parameters.add(new BasicNameValuePair("client_secret", clientSecret));
            parameters.add(new BasicNameValuePair("scope", "openid offline_access"));
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", tokenURL, null, parameters, null, null);
            LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                String accessToken = responseEntityJsonObject.get("access_token").getAsString();
                LOGGER.debug("accessToken is " + accessToken);
                response.setAccessToken(accessToken);
                if (responseEntityJsonObject.get("refresh_token") != null) {
                    String refreshToken = responseEntityJsonObject.get("refresh_token").getAsString();
                    LOGGER.debug("refreshToken is " + refreshToken);
                    response.setRefreshToken(refreshToken);
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

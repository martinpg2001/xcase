package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.impl.simple.objects.SalesforceAccountImpl;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.GetUserRequest;
import com.xcase.salesforce.transputs.GetUserResponse;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserMethod extends BaseSalesforceMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public GetUserResponse getUser(GetUserRequest request) throws IOException, SalesforceException {
        LOGGER.debug("starting getUser()");
        GetUserResponse response = SalesforceResponseFactory.createGetUserResponse();
        LOGGER.debug("created account response");
        String accessToken = request.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String userId = request.getUserId();
        LOGGER.debug("userId is " + userId);
        StringBuffer urlBuff = super.getApiUrl("sobjects/User");
        urlBuff.append("/" + userId);
        String endPoint = urlBuff.toString();
        LOGGER.debug("endPoint is " + endPoint);
        String bearerString = "Bearer " + accessToken;
        LOGGER.debug("bearerString is " + bearerString);
        Header header = new BasicHeader("Authorization", bearerString);
        LOGGER.debug("created Authorization header");
        Header[] headers = {header};
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        try {
            JsonElement jsonElement = httpManager.doJsonGet(endPoint, headers, parameters);
            if (!jsonElement.isJsonNull()) {
                LOGGER.debug("jsonElement is " + jsonElement.toString());
                JsonObject jsonObject = (JsonObject) jsonElement;
            } else {
                String status = SalesforceConstant.STATUS_NOT_LOGGED_IN;
                response.setStatus(status);
            }
        } catch (Exception e) {
            throw new SalesforceException("Failed to parse to a document.", e);
        }

        return response;
    }

}

package com.xcase.intapp.document.impl.simple.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.document.factories.DocumentResponseFactory;
import com.xcase.intapp.document.transputs.GetCategoriesRequest;
import com.xcase.intapp.document.transputs.GetCategoriesResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetCategoriesMethod extends BaseDocumentMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
	public GetCategoriesResponse getCategories(GetCategoriesRequest request) {
        LOGGER.debug("starting getCategories()");
        GetCategoriesResponse response = DocumentResponseFactory.createGetCategoriesResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            endPoint = baseVersionUrl + request.getOperationPath();
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createDocumentAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header acceptLanguageHeader = new BasicHeader("Accept-Language", "en-US");
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, acceptLanguageHeader, authorizationHeader};
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("Authorization", "Bearer " + accessToken));
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            if (responseCode == 200) {
            	handleExpectedResponseCode(response, commonHttpResponse);
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
	}

}

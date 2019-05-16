package com.xcase.intapp.document.impl.simple.methods;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.intapp.document.factories.DocumentResponseFactory;
import com.xcase.intapp.document.transputs.SaveTemplateRequest;
import com.xcase.intapp.document.transputs.SaveTemplateResponse;

public class SaveTemplateMethod extends BaseDocumentMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
	public SaveTemplateResponse saveTemplate(SaveTemplateRequest request) {
        LOGGER.debug("starting saveTemplate()");
        SaveTemplateResponse response = DocumentResponseFactory.createSaveTemplateResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            endPoint = baseVersionUrl + request.getOperationPath();
            String category = request.getCategory();
            String name = request.getName();
            endPoint = endPoint.replace("{category}", category).replace("{name}", name);
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
            parameters.add(new BasicNameValuePair("category", category));
            parameters.add(new BasicNameValuePair("name", name));
            HashMap<String, byte[]> byteArrayHashMap = new HashMap<String, byte[]>();
            String item1 = request.getItem1();
            byte[] item2 = request.getItem2();
            byteArrayHashMap.put(item1, item2);
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMultipartByteArrayPost(endPoint, byteArrayHashMap, headers, parameters, null);
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

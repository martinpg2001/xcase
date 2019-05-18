package com.xcase.intapp.document.impl.simple.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.intapp.document.factories.DocumentResponseFactory;
import com.xcase.intapp.document.transputs.DeleteTemplateRequest;
import com.xcase.intapp.document.transputs.DeleteTemplateResponse;
import com.xcase.intapp.document.transputs.SaveTemplateResponse;

public class DeleteTemplateMethod extends BaseDocumentMethod {

	public DeleteTemplateResponse deleteTemplate(DeleteTemplateRequest request) {
        LOGGER.debug("starting deleteTemplate()");
        DeleteTemplateResponse response = DocumentResponseFactory.createDeleteTemplateResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            endPoint = baseVersionUrl + request.getOperationPath();
            String templateId = request.getId();
            endPoint = endPoint.replace("{templateId}", templateId);
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
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseDelete(endPoint, headers, parameters, null);
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

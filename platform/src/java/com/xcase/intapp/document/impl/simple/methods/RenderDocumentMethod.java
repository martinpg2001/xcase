package com.xcase.intapp.document.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.impl.simple.core.MultiPartContent;
import com.xcase.intapp.document.factories.DocumentResponseFactory;
import com.xcase.intapp.document.transputs.RenderDocumentRequest;
import com.xcase.intapp.document.transputs.RenderDocumentResponse;
import com.xcase.intapp.document.transputs.SaveTemplateResponse;

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

public class RenderDocumentMethod extends BaseDocumentMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
	public RenderDocumentResponse renderDocument(RenderDocumentRequest request) {
        LOGGER.debug("starting renderDocument()");
        RenderDocumentResponse response = DocumentResponseFactory.createRenderDocumentResponse();
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
            Header acceptHeader = new BasicHeader("Accept", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            Header acceptLanguageHeader = new BasicHeader("Accept-Language", "en-US");
            Header contentTypeHeader = new BasicHeader("Accept", "*/*");
            Header[] headers = {acceptHeader, acceptLanguageHeader, authorizationHeader, contentTypeHeader};
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            HashMap<String, MultiPartContent> multiPartContentHashMap = new HashMap<String, MultiPartContent>();
            HashMap<String, byte[]> byteArrayHashMap = new HashMap<String, byte[]>();
            String dataItem1 = request.getDataItem1();
            LOGGER.debug("dataItem1 is " + dataItem1);
            byte[] dataItem2 = request.getDataItem2();
            String dataItem3 = request.getDataItem3();
            byteArrayHashMap.put(dataItem1, dataItem2);
            multiPartContentHashMap.put(dataItem1, new MultiPartContent(true, dataItem2, dataItem3));
            String fileItem1 = request.getFileItem1();
            LOGGER.debug("fileItem1 is " + fileItem1);
            byte[] fileItem2 = request.getFileItem2();
            String fileItem3 = request.getfileItem3();
            byteArrayHashMap.put(fileItem1, fileItem2);
            multiPartContentHashMap.put(fileItem1, new MultiPartContent(false, fileItem2, fileItem3));
            LOGGER.debug("multiPartContentHashMap has size " + multiPartContentHashMap.size());
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMultipartContentHashMapPost(endPoint, multiPartContentHashMap, headers, parameters, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            if (responseCode == 201) {
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

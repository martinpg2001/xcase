package com.xcase.klearnow.impl.simple.methods;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.klearnow.factories.KlearNowResponseFactory;
import com.xcase.klearnow.transputs.UploadDocumentsRequest;
import com.xcase.klearnow.transputs.UploadDocumentsResponse;

public class UploadDocumentsMethod extends BaseKlearNowMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UploadDocumentsResponse uploadDocuments(UploadDocumentsRequest request) {
        LOGGER.debug("starting uploadDocuments()");
        try {
        	UploadDocumentsResponse response = KlearNowResponseFactory.createUploadDocumentsResponse();
            String endPoint = request.getAPIUrl() + "upload/shipment" + "/" + request.getShipmentId();
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header accessTokenHeader = createAccessTokenHeader(accessToken);
            LOGGER.debug("created accessTokenHeader");
            Header contentTypeHeader = createContentTypeHeader("multipart/form-data");
            Header[] headers = {accessTokenHeader, contentTypeHeader};
            HashMap<String, File> nameValueMap = request.getDataMap();
            LOGGER.debug("nameValueMap is " + nameValueMap);
            String result = httpManager.doMultipartPost(endPoint, nameValueMap, headers, null);
            LOGGER.debug("done multipart post");
            LOGGER.debug("result is " + result);
            return response;
        } catch (Exception e) {
            LOGGER.warn("exception sending message: " + e.getMessage());
        }

        return null;
    }
}

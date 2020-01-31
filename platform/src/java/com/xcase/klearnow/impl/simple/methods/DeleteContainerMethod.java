package com.xcase.klearnow.impl.simple.methods;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.klearnow.factories.KlearNowResponseFactory;
import com.xcase.klearnow.transputs.DeleteContainerRequest;
import com.xcase.klearnow.transputs.DeleteContainerResponse;

public class DeleteContainerMethod extends BaseKlearNowMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public DeleteContainerResponse deleteContainer(DeleteContainerRequest request) {
        LOGGER.debug("starting deleteContainer()");
        String endPoint = null;
        try {
        	DeleteContainerResponse response = KlearNowResponseFactory.createDeleteContainerResponse();
            if (request.getContainerId() != null) {
                endPoint = request.getAPIUrl() + "container" + "/" + request.getContainerId();
            } else {
            	endPoint = request.getAPIUrl() + "shipment" + "/" + request.getShipmentId() + "/container/" + request.getContainerNumber();
            }
            
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header accessTokenHeader = createAccessTokenHeader(accessToken);
            LOGGER.debug("created accessTokenHeader");
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {accessTokenHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("DELETE", endPoint, headers, null, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 202) {
                processExpectedResponseCode(response, commonHttpResponse);
            } else {
                LOGGER.warn("unexpected response code " + responseCode);
                processUnexpectedResponseCode(response, commonHttpResponse);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception sending message: " + e.getMessage());
        }

        return null;
    }
}

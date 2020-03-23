package com.xcase.klearnow.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.klearnow.constant.KlearNowConstant;
import com.xcase.klearnow.factories.KlearNowResponseFactory;
import com.xcase.klearnow.transputs.CreateShipmentRequest;
import com.xcase.klearnow.transputs.CreateShipmentResponse;
import com.xcase.klearnow.transputs.UpdateShipmentRequest;
import com.xcase.klearnow.transputs.UpdateShipmentResponse;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateShipmentMethod extends BaseKlearNowMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateShipmentResponse updateShipment(UpdateShipmentRequest request) {
        LOGGER.debug("starting updateShipment()");
        try {
            UpdateShipmentResponse response = KlearNowResponseFactory.createUpdateShipmentResponse();
            String endPoint = request.getAPIUrl() + "shipment" + "/" + request.getShipmentId();
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header accessTokenHeader = createAccessTokenHeader(accessToken);
            LOGGER.debug("created accessTokenHeader");
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {accessTokenHeader, contentTypeHeader};
            String entityMessage = request.getMessage();
            LOGGER.debug("entityMessage is " + entityMessage);
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("PATCH", endPoint, headers, null, entityMessage, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == KlearNowConstant.UPDATE_SHIPMENT) {
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

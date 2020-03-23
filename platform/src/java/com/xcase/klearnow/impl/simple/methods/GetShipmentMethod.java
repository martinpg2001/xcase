package com.xcase.klearnow.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.klearnow.constant.KlearNowConstant;
import com.xcase.klearnow.factories.KlearNowResponseFactory;
import com.xcase.klearnow.transputs.GetShipmentRequest;
import com.xcase.klearnow.transputs.GetShipmentResponse;
import com.xcase.klearnow.transputs.UpdateShipmentRequest;
import com.xcase.klearnow.transputs.UpdateShipmentResponse;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetShipmentMethod extends BaseKlearNowMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetShipmentResponse getShipment(GetShipmentRequest request) {
        LOGGER.debug("starting getShipment()");
        try {
            GetShipmentResponse response = KlearNowResponseFactory.createGetShipmentResponse();
            String endPoint = request.getAPIUrl() + "shipment" + "/" + request.getShipmentId();
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header accessTokenHeader = createAccessTokenHeader(accessToken);
            LOGGER.debug("created accessTokenHeader");
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {accessTokenHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("GET", endPoint, headers, null, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == KlearNowConstant.GET_SHIPMENT) {
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

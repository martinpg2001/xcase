package com.xcase.klearnow.impl.simple.methods;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.klearnow.constant.KlearNowConstant;
import com.xcase.klearnow.factories.KlearNowResponseFactory;
import com.xcase.klearnow.transputs.CreateSupplierAdminRequest;
import com.xcase.klearnow.transputs.CreateSupplierAdminResponse;

public class CreateSupplierAdminMethod extends BaseKlearNowMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateSupplierAdminResponse createSupplierAdmin(CreateSupplierAdminRequest request) {
        LOGGER.debug("starting createSupplierAdmin()");
        try {
        	CreateSupplierAdminResponse response = KlearNowResponseFactory.createCreateSupplierAdminResponse();
            String endPoint = request.getAPIUrl() + "supplier";
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header accessTokenHeader = createAccessTokenHeader(accessToken);
            LOGGER.debug("created accessTokenHeader");
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {accessTokenHeader, contentTypeHeader};
            String entityMessage = request.getMessage();
            LOGGER.debug("entityMessage is " + entityMessage);
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, null, entityMessage, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == KlearNowConstant.CREATE_SUPPLIER_ADMIN) {
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

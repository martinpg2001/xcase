package com.xcase.klearnow.impl.simple.methods;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.klearnow.constant.KlearNowConstant;
import com.xcase.klearnow.factories.KlearNowResponseFactory;
import com.xcase.klearnow.transputs.CreateActorRequest;
import com.xcase.klearnow.transputs.CreateActorResponse;
import com.xcase.klearnow.transputs.DeleteActorRequest;
import com.xcase.klearnow.transputs.DeleteActorResponse;

public class DeleteActorMethod extends BaseKlearNowMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public DeleteActorResponse deleteActor(DeleteActorRequest request) {
        LOGGER.debug("starting deleteActor()");
        try {
        	DeleteActorResponse response = KlearNowResponseFactory.createDeleteActorResponse();
            String endPoint = request.getAPIUrl() + "actor/" + request.getActorId();
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
            if (responseCode == KlearNowConstant.DELETE_ACTOR) {
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

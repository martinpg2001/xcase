/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxAbstractFile;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.MoveRequest;
import com.xcase.box.transputs.MoveResponse;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class MoveMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method moves a file or folder to another folder.
     *
     * 'target' param can be either 'file' or 'folder' depending on what do you
     * want to move, 'target_id' is the id of a file or folder to be moved,
     * 'destination_id' is the destination folder id.
     *
     * On a successful result, status will be 's_move_node'. If the result
     * wasn't successful, status field can be: 'e_move_node', 'not_logged_in',
     * 'application_restricted'.
     *
     * @param moveRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public MoveResponse move(MoveRequest moveRequest) throws IOException, BoxException {
        MoveResponse moveResponse = BoxResponseFactory.createMoveResponse();
        String apiKey = moveRequest.getApiKey();
        LOGGER.debug("apiKey is " + apiKey);
        String accessToken = moveRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String target = moveRequest.getTarget();
        LOGGER.debug("target is " + target);
        String targetId = moveRequest.getTargetId();
        LOGGER.debug("targetId is " + targetId);
        String destinationId = moveRequest.getDestinationId();
        LOGGER.debug("destinationId is " + destinationId);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            StringBuffer urlBuff = null;
            if ("folders".equalsIgnoreCase(target)) {
                urlBuff = super.getApiUrl("folders");
            } else {
                urlBuff = super.getApiUrl("files");
            }

            urlBuff.append("/" + targetId + "/copy");
            String filesApiUrl = urlBuff.toString();
            LOGGER.debug("filesApiUrl is " + filesApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            String entityString = "{\"parent\": {\"id\": \"" + destinationId + "\"}}";
            LOGGER.debug("entityString is " + entityString);
            try {
                JsonElement jsonElement = httpManager.doJsonPost(filesApiUrl, headers, null, entityString);
                if (!jsonElement.isJsonNull()) {
                    JsonObject jsonObject = (JsonObject) jsonElement;
                    JsonElement typeElement = jsonObject.get("type");
                    JsonElement idElement = jsonObject.get("id");
                    JsonElement sequence_idElement = jsonObject.get("sequence_id");
                    JsonElement nameElement = jsonObject.get("name");
                    BoxAbstractFile boxAbstractFile = ConverterUtils.toBoxAbstractFile(jsonObject);
                    moveResponse.setFile(boxAbstractFile);
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(moveResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            Element targetElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_TARGET);
            Element targetIdElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_TARGET_ID);
            Element destinationIdElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_DESTINATION_ID);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(targetElm);
            requestElm.add(targetIdElm);
            requestElm.add(destinationIdElm);
            actionElm.setText(BoxConstant.ACTION_NAME_MOVE);
            apiKeyElm.setText(apiKey);
            targetElm.setText(target);
            targetIdElm.setText(targetId);
            destinationIdElm.setText(destinationId);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                moveResponse.setStatus(status);
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(moveResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return moveResponse;
    }
}

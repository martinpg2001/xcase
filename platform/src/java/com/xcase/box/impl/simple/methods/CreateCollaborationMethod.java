/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxCollaboration;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.CreateCollaborationRequest;
import com.xcase.box.transputs.CreateCollaborationResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.commons.codec.net.URLCodec;
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
public class CreateCollaborationMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method creates a new collaboration.
     *
     * 'parent_id' param is the id of a folder in which a new file will be
     * created, 'name' param is the name of a new folder. Set 'share' to 1 if
     * you want to share a folder publicly.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param createCollaborationRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public CreateCollaborationResponse createCollaboration(CreateCollaborationRequest createCollaborationRequest) throws IOException, BoxException {
        LOGGER.debug("starting createCollaboration()");
        CreateCollaborationResponse createCollaborationResponse = BoxResponseFactory.createCreateCollaborationResponse();
        String accessToken = createCollaborationRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String folderId = createCollaborationRequest.getFolderId();
        String accessibleBy = createCollaborationRequest.getAccessibleBy();
        String role = createCollaborationRequest.getRole();
        boolean notify = createCollaborationRequest.getNotify();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            LOGGER.debug("set headers");
            String entityString = "{ \"item\": {\"id\":\"" + folderId + "\", \"type\":\"folder\" }, \"accessible_by\": {\"id\": \"" + accessibleBy + "\", \"type\":\"user\" }, \"role\":\"" + role + "\" }";
            LOGGER.debug("entityString is " + entityString);
            LOGGER.debug("about to build collaborationsApiUrl");
            StringBuffer urlBuff = super.getApiUrl("collaborations");
            if (notify) {
                urlBuff.append(CommonConstant.QUESTION_MARK_STRING + "notify=true");
            }

            String collaborationsApiUrl = urlBuff.toString();
            LOGGER.debug("collaborationsApiUrl is " + collaborationsApiUrl);
            try {
                String createCollaborationString = httpManager.doStringPost(collaborationsApiUrl, headers, null, entityString, null);
                LOGGER.info("done post");
                LOGGER.debug("createCollaborationString is " + createCollaborationString);
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(createCollaborationString);
                LOGGER.info("about to convert jsonObject to BoxCollaboration");
                BoxCollaboration boxCollaboration = ConverterUtils.toBoxCollaboration(jsonObject);
                LOGGER.info("converted jsonObject to BoxCollaboration");
                createCollaborationResponse.setCollaboration(boxCollaboration);
                LOGGER.info("set collaboration in response");
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(createCollaborationResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            Element parentIdElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_PARENT_ID);
            Element nameElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_NAME);
            Element shareElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_SHARE);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(parentIdElm);
            requestElm.add(nameElm);
            requestElm.add(shareElm);
            actionElm.setText(BoxConstant.ACTION_NAME_CREATE_FOLDER);
            parentIdElm.setText(folderId);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                createCollaborationResponse.setStatus(status);
                if (BoxConstant.STATUS_CREATE_OK.equals(status)) {
                    Element folderElm = responseElm.element(BoxConstant.PARAM_NAME_FOLDER);
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(createCollaborationResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return createCollaborationResponse;
    }
}

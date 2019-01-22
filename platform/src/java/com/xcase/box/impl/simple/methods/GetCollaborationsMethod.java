/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxObjectFactory;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxAccessibleBy;
import com.xcase.box.objects.BoxCollaboration;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.GetCollaborationsRequest;
import com.xcase.box.transputs.GetCollaborationsResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
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
public class GetCollaborationsMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets collaboration info for a folder.
     *
     * 'parent_id' param is the id of a folder in which a new folder will be
     * created, 'name' param is the name of a new folder. Set 'share' to 1 if
     * you want to share a folder publicly.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param getCollaborationsRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetCollaborationsResponse getCollaborations(GetCollaborationsRequest getCollaborationsRequest) throws IOException, BoxException {
        LOGGER.debug("starting getCollaboration()");
        GetCollaborationsResponse getCollaborationResponse = BoxResponseFactory.createGetCollaborationsResponse();
        String accessToken = getCollaborationsRequest.getAccessToken();
        String folderId = getCollaborationsRequest.getFolderId();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            StringBuffer urlBuff = super.getApiUrl("folders");
            String foldersApiUrl = urlBuff.toString();
            LOGGER.debug("foldersApiUrl is " + foldersApiUrl);
            urlBuff.append("/" + folderId + "/collaborations");
            String folderUrl = urlBuff.toString();
            Header header = new BasicHeader("Authorization", "Bearer " + accessToken);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String folderCollaborationString = httpManager.doStringGet(folderUrl, headers, null);
                LOGGER.debug("folderCollaborationString is " + folderCollaborationString);
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(folderCollaborationString);
                JsonArray entriesArray = jsonObject.getAsJsonArray("entries");
                List<BoxCollaboration> boxCollaborationList = new ArrayList<BoxCollaboration>();
                for (int i = 0; i < entriesArray.size(); i++) {
                    BoxCollaboration boxCollaboration = BoxObjectFactory.createBoxCollaboration();
                    BoxAccessibleBy boxAccessibleBy = BoxObjectFactory.createBoxAccessibleBy();
                    JsonObject abstractFileJsonObject = (JsonObject) entriesArray.get(i);
                    JsonElement typeElement = abstractFileJsonObject.get("type");
                    String type = typeElement.getAsString();
                    LOGGER.debug("type is " + type);
                    JsonElement idElement = abstractFileJsonObject.get("id");
                    String id = idElement.getAsString();
                    LOGGER.debug("id is " + id);
                    boxCollaboration.setId(id);
                    JsonObject accessibleByObject = (JsonObject) abstractFileJsonObject.get("accessible_by");
                    JsonElement accessibleByTypeElement = accessibleByObject.get("type");
                    String accessibleByType = accessibleByTypeElement.getAsString();
                    LOGGER.debug("accessibleByType is " + accessibleByType);
                    boxAccessibleBy.setType(accessibleByType);
                    JsonElement accessibleByIdElement = accessibleByObject.get("id");
                    String accessibleById = accessibleByIdElement.getAsString();
                    LOGGER.debug("accessibleById is " + accessibleById);
                    boxAccessibleBy.setId(accessibleById);
                    JsonElement accessibleByNameElement = accessibleByObject.get("name");
                    String accessibleByName = accessibleByNameElement.getAsString();
                    LOGGER.debug("accessibleByName is " + accessibleByName);
                    boxAccessibleBy.setName(accessibleByName);
                    JsonElement accessibleByLoginElement = accessibleByObject.get("login");
                    String accessibleByLogin = accessibleByLoginElement.getAsString();
                    LOGGER.debug("accessibleByLogin is " + accessibleByLogin);
                    boxAccessibleBy.setLogin(accessibleByLogin);
                    boxCollaboration.setAccessibleBy(boxAccessibleBy);
                    JsonElement roleElement = abstractFileJsonObject.get("role");
                    String role = roleElement.getAsString();
                    boxCollaboration.setRole(role);
                    boxCollaborationList.add(boxCollaboration);
                }

                getCollaborationResponse.setCollaborations(boxCollaborationList);
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                BoxException be = new BoxException("failed to parse to a document.", e);
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
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                if (BoxConstant.STATUS_CREATE_OK.equals(status)) {

                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return getCollaborationResponse;
    }
}

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
import com.xcase.box.transputs.GetPendingCollaborationsRequest;
import com.xcase.box.transputs.GetPendingCollaborationsResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class GetPendingCollaborationsMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets pending collaborations.
     *
     * @param getPendingCollaborationsRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetPendingCollaborationsResponse getPendingCollaborations(GetPendingCollaborationsRequest getPendingCollaborationsRequest) throws IOException, BoxException {
        LOGGER.debug("starting getCollaboration()");
        GetPendingCollaborationsResponse getPendingCollaborationsResponse = BoxResponseFactory.createGetPendingCollaborationsResponse();
        String apiKey = getPendingCollaborationsRequest.getApiKey();
        String accessToken = getPendingCollaborationsRequest.getAccessToken();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("collaborations");
            String collaborationsApiUrl = urlBuff.toString();
            LOGGER.debug("collaborationsApiUrl is " + collaborationsApiUrl);
            urlBuff.append(CommonConstant.QUESTION_MARK_STRING + "status" + CommonConstant.EQUALS_SIGN_STRING + "pending");
            String pendingCollaborationsApiUrl = urlBuff.toString();
            LOGGER.debug("pendingCollaborationsApiUrl is " + pendingCollaborationsApiUrl);
            Header header = new BasicHeader("Authorization", "Bearer " + accessToken);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String pendingCollaborationsString = httpManager.doStringGet(pendingCollaborationsApiUrl, headers, null);
                LOGGER.debug("folderCollaborationString is " + pendingCollaborationsString);
                JsonObject jsonObject = (JsonObject) JsonParser.parseString(pendingCollaborationsString);
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

                getPendingCollaborationsResponse.setCollaborations(boxCollaborationList);
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                BoxException be = new BoxException("failed to parse to a document.", e);
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_XML");
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return getPendingCollaborationsResponse;
    }
}

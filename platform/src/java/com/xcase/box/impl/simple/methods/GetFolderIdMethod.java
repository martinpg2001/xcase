/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.GetFolderIdRequest;
import com.xcase.box.transputs.GetFolderIdResponse;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class GetFolderIdMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param getFolderIdRequest
     * @return getFolderIdResponse
     * @throws IOException
     * @throws BoxException
     */
    public GetFolderIdResponse getFolderId(GetFolderIdRequest getFolderIdRequest) throws IOException, BoxException {
        LOGGER.debug("starting getFolderId()");
        GetFolderIdResponse getFolderIdResponse = BoxResponseFactory.createGetFolderIdResponse();
        String apiKey = getFolderIdRequest.getApiKey();
        LOGGER.debug("apiKey is " + apiKey);
        String accessToken = getFolderIdRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String folderPath = getFolderIdRequest.getFolderPath();
        LOGGER.debug("folderPath is " + folderPath);
        try {
            if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
                LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
                /* Assume folderPath always starts with / and that this corresponds to the 0 root directory */
                String[] folderPathArray = folderPath.split("/");
                LOGGER.debug("folderPathArray length is " + folderPathArray.length);
                String folderId = "0";
                for (int i = 1; i < folderPathArray.length; i++) {
                    String folderName = folderPathArray[i];
                    LOGGER.debug("folderName is " + folderName);
                    folderId = getChildFolderId(folderId, folderName, accessToken);
                    LOGGER.debug("folderId is " + folderId);
                }

                getFolderIdResponse.setFolderId(folderId);
            }
        } catch (Exception e) {

        }

        return getFolderIdResponse;
    }

    public String getChildFolderId(String parentFolderId, String folderName, String accessToken) {
        LOGGER.debug("starting getChildFolderId()");
        String folderId = null;
        StringBuffer urlBuff = super.getApiUrl("folders");
        String foldersApiUrl = urlBuff.toString();
        LOGGER.debug("foldersApiUrl is " + foldersApiUrl);
        urlBuff.append("/" + parentFolderId + "/items");
        String folderItemsUrl = urlBuff.toString();
        LOGGER.debug("folderItemsUrl is " + folderItemsUrl);
        Header header = new BasicHeader("Authorization", "Bearer " + accessToken);
        LOGGER.debug("created Authorization header");
        Header[] headers = {header};
        try {
            JsonElement jsonElement = httpManager.doJsonGet(folderItemsUrl, headers, null);
            JsonObject jsonObject = (JsonObject) jsonElement;
            JsonArray entriesArray = jsonObject.getAsJsonArray("entries");
            for (int i = 0; i < entriesArray.size(); i++) {
                JsonObject abstractFileJsonObject = (JsonObject) entriesArray.get(i);
                JsonElement typeElement = abstractFileJsonObject.get("type");
                String type = typeElement.getAsString();
                JsonElement idElement = abstractFileJsonObject.get("id");
                String id = idElement.getAsString();
                JsonElement nameElement = abstractFileJsonObject.get("name");
                String name = nameElement.getAsString();
                if (type.equalsIgnoreCase("folder") && name.equalsIgnoreCase(folderName)) {
                    folderId = id;
                    break;
                }
            }
        } catch (Exception e) {

        }

        return folderId;
    }
}

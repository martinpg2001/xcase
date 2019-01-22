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
import com.xcase.box.transputs.GetFileIdRequest;
import com.xcase.box.transputs.GetFileIdResponse;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class GetFileIdMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param getFileIdRequest
     * @return getFileIdResponse
     * @throws IOException
     * @throws BoxException
     */
    public GetFileIdResponse getFileId(GetFileIdRequest getFileIdRequest) throws IOException, BoxException {
        LOGGER.debug("starting getFileId()");
        GetFileIdResponse getFileIdResponse = BoxResponseFactory.createGetFileIdResponse();
        String apiKey = getFileIdRequest.getApiKey();
        LOGGER.debug("apiKey is " + apiKey);
        String accessToken = getFileIdRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String filePath = getFileIdRequest.getFilePath();
        LOGGER.debug("filePath is " + filePath);
        try {
            if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
                LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
                /* Assume folderPath always starts with / and that this corresponds to the 0 root directory */
                String[] filePathArray = filePath.split("/");
                LOGGER.debug("folderPathArray length is " + filePathArray.length);
                String folderId = "0";
                for (int i = 1; i < filePathArray.length - 1; i++) {
                    String folderName = filePathArray[i];
                    LOGGER.debug("folderName is " + folderName);
                    folderId = getChildFolderId(folderId, folderName, accessToken);
                    LOGGER.debug("folderId is " + folderId);
                }

                String fileName = filePathArray[filePathArray.length - 1];
                LOGGER.debug("fileName is " + fileName);
                String fileId = getChildFileId(folderId, fileName, accessToken);
                getFileIdResponse.setFileId(fileId);
            }
        } catch (Exception e) {

        }

        return getFileIdResponse;
    }

    public String getChildFileId(String parentFolderId, String fileName, String accessToken) {
        LOGGER.debug("starting getChildFolderId()");
        String fileId = null;
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
                if (type.equalsIgnoreCase("file") && name.equalsIgnoreCase(fileName)) {
                    fileId = id;
                    break;
                }
            }
        } catch (Exception e) {

        }

        return fileId;
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

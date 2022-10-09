/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxObjectFactory;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.objects.Conflict;
import com.xcase.box.impl.simple.objects.FileExists;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxFile;
import com.xcase.box.objects.UploadResult;
import com.xcase.box.transputs.UploadRequest;
import com.xcase.box.transputs.UploadResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.*;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class UploadMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * upload.
     *
     * @param uploadRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public UploadResponse upload(UploadRequest uploadRequest) throws Exception, IOException, BoxException {
        LOGGER.debug("starting upload()");
        UploadResponse uploadResponse = BoxResponseFactory.createUploadResponse();
        LOGGER.debug("created uploadResponse");
        String accessToken = uploadRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String directoryName = uploadRequest.getDirectoryName();
        LOGGER.debug("directoryName is " + directoryName);
        String folderId = uploadRequest.getFolderId();
        LOGGER.debug("folderId is " + folderId);
        String fileName = uploadRequest.getDirectoryName();
        LOGGER.debug("fileName is " + fileName);
        HashMap<String, File> nameValueMap = uploadRequest.getDataMap();
        if (nameValueMap != null) {
            boolean asFile = uploadRequest.isAsFile();
            LOGGER.debug("asFile is " + asFile);
            StringBuffer urlBuff = super.getApiUploadUrl("files");
            urlBuff.append(CommonConstant.SLASH_STRING + "content");
            String uploadFilesApiUrl = urlBuff.toString();
            LOGGER.debug("uploadFilesApiUrl is " + uploadFilesApiUrl);
            ArrayList<Header> headerArrayList = new ArrayList<Header>();
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header authorizationHeader = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            headerArrayList.add(authorizationHeader);
            if (uploadRequest.getOnBehalfOf() != null) {
                String onBehalfOfString = uploadRequest.getOnBehalfOf();
                LOGGER.debug("onBehalfOfString is " + onBehalfOfString);
                Header onBehalfOfHeader = new BasicHeader("On-Behalf-Of", onBehalfOfString);
                LOGGER.debug("created On-Behalf-Of header");
                headerArrayList.add(onBehalfOfHeader);
            }

            Header[] headers = new Header[headerArrayList.size()];
            headerArrayList.toArray(headers);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            String attributesString = "{\"name\":\"" + fileName + "\", \"parent\":{\"id\":\"" + folderId + "\"}}";
            parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_ATTRIBUTES, attributesString));
            LOGGER.debug("added attributes parameter");
            String result = null;
            if (asFile) {
                LOGGER.debug("upload as file");
                List<File> fileList = new ArrayList<File>();
                Iterator iterator = nameValueMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String entryFileName = (String) entry.getKey();
                    LOGGER.debug("entryFileName is " + entryFileName);
                    File inFile = (File) entry.getValue();
                    fileList.add(inFile);
                }

                LOGGER.debug("about to do multipart post");
                result = httpManager.doMultipartPost(uploadFilesApiUrl, fileList, headers, parameters);
                LOGGER.debug("done multipart post");
            } else {
                LOGGER.debug("upload as map");
                result = httpManager.doMultipartPost(uploadFilesApiUrl, nameValueMap, headers, parameters);
                LOGGER.debug("done multipart post");
            }

            LOGGER.debug("result is " + result);
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(result);
            JsonElement totalCountElement = jsonObject.get("total_count");
            LOGGER.debug("totalCountElement is " + totalCountElement);
            JsonArray entriesArray = jsonObject.getAsJsonArray("entries");
            List fileStatusList = toFileStatusList(entriesArray);
            uploadResponse.setUploadResultList(fileStatusList);
        } else {
            LOGGER.debug("nameValueMap is null");
            try {
                if (folderId == null) {
                    folderId = "0";
                }

                LOGGER.debug("folderId is " + folderId);
                uploadDirectory(folderId, new File(directoryName), accessToken);
            } catch (Exception e) {
                LOGGER.debug("exception while uploading directory: " + e.getMessage());
                throw new BoxException(e.getMessage());
            }
        }

        return uploadResponse;
    }

    /**
     *
     * @param filesElm file element
     * @return file status object list
     */
    private List toFileStatusList(JsonArray filesJsonArray) {
        LOGGER.debug("starting toFileStatusList()");
        List list = new ArrayList();
        if (filesJsonArray != null) {
            LOGGER.debug("filesJsonArray is not null");
            Iterator<JsonElement> fileIterator = filesJsonArray.iterator();
            while (fileIterator.hasNext()) {
                LOGGER.debug("next file element");
                JsonElement fileElement = fileIterator.next();
                JsonObject fileObject = fileElement.getAsJsonObject();
                UploadResult uploadFileStatus = BoxObjectFactory.createUploadResult();
                BoxFile soapFileInfo = BoxObjectFactory.createBoxFile();
                uploadFileStatus.setFile(soapFileInfo);
                String fileName = fileObject.get("name").getAsString();
                soapFileInfo.setFileName(fileName);
                LOGGER.debug("fileName is " + fileName);
                JsonElement errorElement = fileObject.get("error");
                if (errorElement == null) {
                    LOGGER.debug("error string is null or empty");
                    String id = fileObject.get("id").getAsString();
                    LOGGER.debug("id is " + id);
                    soapFileInfo.setFileId(id);
                    JsonObject parentObject = fileObject.getAsJsonObject("parent");
                    String folderId = parentObject.get("id").getAsString();
                    LOGGER.debug("folderId is " + folderId);
                    soapFileInfo.setFolderId(folderId);
                    soapFileInfo.setShared(false);
                    JsonElement sharedElement = fileObject.get("shared_link");
                    if (!sharedElement.isJsonNull()) {
                        LOGGER.debug("shared element is not null");
                        JsonObject sharedObject = fileObject.getAsJsonObject("shared_link");
                        String shared = sharedObject.getAsString();
                        LOGGER.debug("shared is " + shared);
                        soapFileInfo.setShared(true);
                        soapFileInfo.setSharedName(shared);
                    }
                } else {
                    uploadFileStatus.setErrorInfo(errorElement.getAsString());
                }

                list.add(uploadFileStatus);
            }
        } else {
            LOGGER.debug("filesJsonArray is null");
        }

        return list;
    }

    /**
     *
     * @param filesElm file element
     * @return file status object list
     */
    private List toFileStatusList(Element filesElm) {
        List list = new ArrayList();
        for (int i = 0; i < filesElm.nodeCount(); i++) {
            UploadResult uploadFileStatus = BoxObjectFactory.createUploadResult();
            BoxFile soapFileInfo = BoxObjectFactory.createBoxFile();
            uploadFileStatus.setFile(soapFileInfo);
            Element fileElm = (Element) filesElm.node(i);
            String fileName = fileElm.attributeValue("file_name");
            soapFileInfo.setFileName(fileName);
            String errorStr = fileElm.attributeValue("error");
            if (errorStr == null || errorStr.length() == 0) {
                String id = fileElm.attributeValue("id");
                String folderId = fileElm.attributeValue("folder_id");
                String shared = fileElm.attributeValue("shared");
                String publicName = fileElm.attributeValue("public_name");
                soapFileInfo.setFileId(id);
                soapFileInfo.setFolderId(folderId);
                if ("1".equals(shared)) {
                    soapFileInfo.setShared(true);
                } else {
                    soapFileInfo.setShared(false);
                }
                soapFileInfo.setSharedName(publicName);

            } else {
                uploadFileStatus.setErrorInfo(errorStr);
            }

            list.add(uploadFileStatus);
        }

        return list;
    }

    public void uploadDirectory(String parentFolderId, File directory, String accessToken) throws Exception {
        LOGGER.debug("starting uploadDirectory()");
        String folderId = getOrCreateFolderId(parentFolderId, directory.getName(), accessToken);
        LOGGER.debug("folderId is " + folderId);
        for (File fileEntry : directory.listFiles()) {
            LOGGER.debug("next file entry " + fileEntry.getName());
            if (fileEntry.isDirectory()) {
                LOGGER.debug("file is directory");
                uploadDirectory(folderId, fileEntry, accessToken);
            } else {
                LOGGER.debug("file is file");
                uploadFile(folderId, fileEntry, accessToken);
            }
        }
    }

    public void uploadFile(String folderId, File file, String accessToken) throws Exception {
        LOGGER.debug("starting uploadFile()");
        LOGGER.debug("folderId is " + folderId);
        LOGGER.debug("file name is " + file.getName());
        LOGGER.debug("file path is " + file.getPath());
        boolean createNewVersion = true;
        StringBuffer urlBuff = super.getApiUrl("files");
        urlBuff.append("/content");
        String filesApiUrl = urlBuff.toString();
        LOGGER.debug("filesApiUrl is " + filesApiUrl);
        String bearerString = "Bearer " + accessToken;
        LOGGER.debug("bearerString is " + bearerString);
        Header header = new BasicHeader("Authorization", bearerString);
        LOGGER.debug("created Authorization header");
        Header[] headers = {header};
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_PARENT_ID, folderId));
        LOGGER.debug("added folderId parameter");
        LOGGER.debug("upload as file");
        List<File> fileList = new ArrayList<File>();
        fileList.add(file);
        LOGGER.debug("about to do multipart post");
        String resultString = httpManager.doMultipartPost(filesApiUrl, fileList, headers, parameters);
        LOGGER.debug("done multipart post with result " + resultString);
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(resultString);
            LOGGER.debug("created jsonObject");
            try {
                int totalCount = jsonObject.get("total_count").getAsInt();
                LOGGER.debug("totalCount is " + totalCount);
            } catch (Exception e) {
                LOGGER.debug("catching exception when getting total count");
                String type = jsonObject.get("type").getAsString();
                LOGGER.debug("type is " + type);
                int status = jsonObject.get("status").getAsInt();
                LOGGER.debug("status is " + status);
                if (status == 409) {
                    LOGGER.debug("file may exist already");
                    if (createNewVersion) {
                        LOGGER.debug("createNewVersion is true");
                        Gson gson = new GsonBuilder().create();
                        FileExists fileExists = gson.fromJson(jsonObject, com.xcase.box.impl.simple.objects.FileExists.class);
                        LOGGER.debug("created fileExists object with status " + fileExists.getStatus());
                        Conflict[] conflicts = fileExists.getContextInfo().getConflicts();
                        for (Conflict conflict : conflicts) {
                            LOGGER.debug("conflict type is " + conflict.getType());
                        }
                        JsonObject contextInfoJsonObject = jsonObject.getAsJsonObject("context_info");
                        JsonArray conflictsArray = contextInfoJsonObject.getAsJsonArray("conflicts");
                        for (JsonElement conflictJsonObject : conflictsArray) {
                            String conflictType = ((JsonObject) conflictJsonObject).get("type").getAsString();
                            if ("file".equalsIgnoreCase(conflictType)) {
                                LOGGER.debug("file exists already");
                                String fileId = ((JsonObject) conflictJsonObject).get("id").getAsString();
                                LOGGER.debug("fileId is " + fileId);
                                StringBuffer uploadNewVersionUrlStringBuffer = super.getApiUrl("files");
                                uploadNewVersionUrlStringBuffer.append("/" + fileId);
                                uploadNewVersionUrlStringBuffer.append("/content");
                                String uploadNewVersionUrl = uploadNewVersionUrlStringBuffer.toString();
                                LOGGER.debug("about to do multipart post to upload new file version");
                                String uploadNewVersionResultString = httpManager.doMultipartPost(uploadNewVersionUrl, fileList, headers, parameters);
                                LOGGER.debug("done multipart post");
                            }
                        }
                    }
                } else if (status == 500) {
                    LOGGER.debug("internal server error");
                }
            }
        } catch (Exception e) {
            LOGGER.debug("failed to parse to a Json object");
            BoxException be = new BoxException("Failed to parse to a Json object.", e);
            throw be;
        }
    }

    public String getOrCreateFolderId(String parentFolderId, String directoryName, String accessToken) throws Exception {
        LOGGER.debug("starting getOrCreateFolderId()");
        LOGGER.debug("parentFolderId is " + parentFolderId);
        LOGGER.debug("directoryName is " + directoryName);
        String folderId = getFolderId(parentFolderId, directoryName, accessToken);
        LOGGER.debug("finished getFolderId()");
        if (folderId == null) {
            try {
                folderId = createFolderId(parentFolderId, directoryName, accessToken);
            } catch (Exception e) {

            }
        }

        return folderId;
    }

    public String createFolderId(String parentFolderId, String directoryName, String accessToken) throws IOException, BoxException {
        LOGGER.debug("starting createFolderId()");
        try {
            StringBuffer urlBuff = super.getApiUrl("folders");
            String foldersApiUrl = urlBuff.toString();
            LOGGER.debug("foldersApiUrl is " + foldersApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            String entityString = "{\"name\":\"" + directoryName + "\", \"parent\": {\"id\": \"" + parentFolderId + "\"}}";
            LOGGER.debug("entityString is " + entityString);
            JsonElement resultElement = httpManager.doJsonPost(foldersApiUrl, headers, null, entityString);
            LOGGER.debug("executed POST");
            JsonObject jsonObject = (JsonObject) resultElement;
            JsonElement typeElement = jsonObject.get("type");
            JsonElement idElement = jsonObject.get("id");
            JsonElement sequence_idElement = jsonObject.get("sequence_id");
            JsonElement nameElement = jsonObject.get("name");
            String folderId = idElement.getAsString();
            LOGGER.debug("folderId is " + folderId);
            return folderId;
        } catch (Exception e) {
            LOGGER.debug("failed to parse to a document");
            BoxException be = new BoxException("Failed to parse to a document.", e);
            throw be;
        }
    }

    public String getFolderId(String parentFolderId, String directoryName, String accessToken) throws Exception {
        LOGGER.debug("starting getFolderId()");
        try {
            StringBuffer urlBuff = super.getApiUrl("folders");
            String foldersUrl = urlBuff.toString();
            LOGGER.debug("foldersUrl is " + foldersUrl);
            urlBuff.append("/" + parentFolderId);
            String parentFolderUrl = urlBuff.toString();
            LOGGER.debug("parentFolderUrl is " + parentFolderUrl);
            Header header = new BasicHeader("Authorization", "Bearer " + accessToken);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                JsonElement jsonElement = httpManager.doJsonGet(parentFolderUrl, headers, null);
                LOGGER.debug("got jsonElement");
                JsonObject parentFolderJsonObject = jsonElement.getAsJsonObject();
                JsonObject itemCollectionJsonObject = parentFolderJsonObject.getAsJsonObject("item_collection");
                JsonArray entriesJsonArray = itemCollectionJsonObject.getAsJsonArray("entries");
                Iterator<JsonElement> entriesIterator = entriesJsonArray.iterator();
                while (entriesIterator.hasNext()) {
                    JsonObject entryJsonObject = (JsonObject) entriesIterator.next();
                    String typeString = entryJsonObject.get("type").getAsString();
                    LOGGER.debug("typeString is " + typeString);
                    String idString = entryJsonObject.get("id").getAsString();
                    LOGGER.debug("idString is " + idString);
                    String nameString = entryJsonObject.get("name").getAsString();
                    LOGGER.debug("nameString is " + nameString);
                    if (typeString.equals("folder") && directoryName.equals(nameString)) {
                        LOGGER.debug("found folder " + directoryName);
                        return idString;
                    }
                }
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a Json object");
                BoxException be = new BoxException("Failed to parse to a Json object.", e);
                throw be;
            }
        } catch (Exception e) {
            LOGGER.debug("failed to parse to a Json object");
            BoxException be = new BoxException("Failed to parse to a Json object.", e);
            throw be;
        }

        return null;
    }
}

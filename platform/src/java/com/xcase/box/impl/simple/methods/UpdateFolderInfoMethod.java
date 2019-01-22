/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.JsonObject;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.UpdateFolderInfoRequest;
import com.xcase.box.transputs.UpdateFolderInfoResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class UpdateFolderInfoMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateFolderInfoResponse updateFolderInfo(UpdateFolderInfoRequest updateFolderInfoRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateFolderInfo()");
        UpdateFolderInfoResponse updateFolderInfoResponse = BoxResponseFactory.createUpdateFolderInfoResponse();
        String apiKey = updateFolderInfoRequest.getApiKey();
        LOGGER.debug("apiKey is " + apiKey);
        String accessToken = updateFolderInfoRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String folderId = updateFolderInfoRequest.getFolderId();
        LOGGER.debug("folderId is " + folderId);
        try {
            if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
                LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
                StringBuffer urlBuff = super.getApiUrl("folders");
                urlBuff.append(CommonConstant.SLASH_STRING);
                urlBuff.append(folderId);
                String url = urlBuff.toString();
                LOGGER.debug("url is " + url);
                String bearerString = "Bearer " + accessToken;
                LOGGER.debug("bearerString is " + bearerString);
                Header header = new BasicHeader("Authorization", bearerString);
                LOGGER.debug("created Authorization header");
                Header[] headers = {header};
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                StringBuffer requestBodyStringBuffer = new StringBuffer("{");
                if (updateFolderInfoRequest.getFolderName() != null) {
                    String folderName = updateFolderInfoRequest.getFolderName();
                    LOGGER.debug("folderName is " + folderName);
                    requestBodyStringBuffer.append("\"name\":\"" + folderName + "\"");
                    LOGGER.debug("appended folderName");
                }

                requestBodyStringBuffer.append("}");
                String requestBody = requestBodyStringBuffer.toString();
                LOGGER.debug("requestBody is " + requestBody);
                JsonObject putJsonObject = (JsonObject) httpManager.doJsonPut(url, headers, data, requestBody);
                LOGGER.debug("done PUT");
            }
        } catch (Exception e) {

        }

        return updateFolderInfoResponse;
    }
}

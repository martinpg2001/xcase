/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.JsonObject;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxShare;
import com.xcase.box.transputs.UpdateFileInfoRequest;
import com.xcase.box.transputs.UpdateFileInfoResponse;
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
public class UpdateFileInfoMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateFileInfoResponse updateFileInfo(UpdateFileInfoRequest updateFileInfoRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateFileInfo()");
        UpdateFileInfoResponse updateFileInfoResponse = BoxResponseFactory.createUpdateFileInfoResponse();
        String apiKey = updateFileInfoRequest.getApiKey();
        LOGGER.debug("apiKey is " + apiKey);
        String accessToken = updateFileInfoRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String fileId = updateFileInfoRequest.getFileId();
        LOGGER.debug("fileId is " + fileId);
        try {
            if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
                LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
                StringBuffer urlBuff = super.getApiUrl("files");
                urlBuff.append(CommonConstant.SLASH_STRING);
                urlBuff.append(fileId);
                String url = urlBuff.toString();
                LOGGER.debug("url is " + url);
                String bearerString = "Bearer " + accessToken;
                LOGGER.debug("bearerString is " + bearerString);
                Header header = new BasicHeader("Authorization", bearerString);
                LOGGER.debug("created Authorization header");
                Header[] headers = {header};
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                boolean firstAttribute = true;
                StringBuffer requestBodyStringBuffer = new StringBuffer("{");
                if (updateFileInfoRequest.getFileName() != null) {
                    String fileName = updateFileInfoRequest.getFileName();
                    LOGGER.debug("fileName is " + fileName);
                    if (!firstAttribute) {
                        requestBodyStringBuffer.append(",");
                    }

                    requestBodyStringBuffer.append("\"name\":\"" + fileName + "\"");
                    LOGGER.debug("appended fileName");
                    firstAttribute = false;
                }

                if (updateFileInfoRequest.getFileDescription() != null) {
                    String fileDescription = updateFileInfoRequest.getFileDescription();
                    LOGGER.debug("fileDescription is " + fileDescription);
                    if (!firstAttribute) {
                        requestBodyStringBuffer.append(",");
                    }

                    requestBodyStringBuffer.append("\"description\":\"" + fileDescription + "\"");
                    LOGGER.debug("appended fileDescription");
                    firstAttribute = false;
                }

                if (updateFileInfoRequest.getFileParent() != null) {
                    String fileParent = updateFileInfoRequest.getFileParent();
                    LOGGER.debug("fileParent is " + fileParent);
                    if (!firstAttribute) {
                        requestBodyStringBuffer.append(",");
                    }

                    requestBodyStringBuffer.append("\"parent\":\"{\"id\":\"" + fileParent + "\"}\"}");
                    LOGGER.debug("appended fileParent");
                    firstAttribute = false;
                }

                if (updateFileInfoRequest.getFileShare() != null) {
                    BoxShare fileShare = updateFileInfoRequest.getFileShare();
                    LOGGER.debug("fileShare is " + fileShare);
                    if (!firstAttribute) {
                        requestBodyStringBuffer.append(",");
                    }

                    requestBodyStringBuffer.append("\"shared_link\":" + fileShare.toString());
                    LOGGER.debug("appended fileShare");
                    firstAttribute = false;
                }

                requestBodyStringBuffer.append("}");
                String requestBody = requestBodyStringBuffer.toString();
                LOGGER.debug("requestBody is " + requestBody);
                JsonObject putJsonObject = (JsonObject) httpManager.doJsonPut(url, headers, data, requestBody);
                LOGGER.debug("done PUT");
            }
        } catch (Exception e) {

        }

        return updateFileInfoResponse;
    }
}

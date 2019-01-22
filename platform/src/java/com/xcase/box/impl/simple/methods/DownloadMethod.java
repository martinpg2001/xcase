/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.DownloadRequest;
import com.xcase.box.transputs.DownloadResponse;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class DownloadMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param downloadRequest request
     * @return response
     * @throws IOException IO exception
     */
    public DownloadResponse download(DownloadRequest downloadRequest) throws IOException, BoxException {
        LOGGER.debug("starting download()");
        DownloadResponse downloadResponse = BoxResponseFactory.createDownloadResponse();
        String accessToken = downloadRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String fileId = downloadRequest.getFileId();
        LOGGER.debug("fileId is " + fileId);
        boolean asFile = downloadRequest.isAsFile();
        File inFile = downloadRequest.getInFile();
        StringBuffer urlBuff = super.getApiUrl("files/");
        urlBuff.append(fileId);
        urlBuff.append("/content");
        String filesApiUrl = urlBuff.toString();
        LOGGER.debug("filesApiUrl is " + filesApiUrl);
        String bearerString = "Bearer " + accessToken;
        LOGGER.debug("bearerString is " + bearerString);
        Header header = new BasicHeader("Authorization", bearerString);
        LOGGER.debug("created Authorization header");
        Header[] headers = {header};
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        downloadResponse.setAsFile(asFile);
        try {
            if (asFile) {
                File result = httpManager.doGetFile(filesApiUrl, inFile, headers, data);
                downloadResponse.setOutFile(result);
            } else {
                byte[] result = httpManager.doGetByteArray(filesApiUrl, headers, data);
                downloadResponse.setRawData(result);
            }
        } catch (Exception e) {
            throw new BoxException(e.getMessage());
        }

        return downloadResponse;
    }
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.factories;

import com.xcase.sharepoint.transputs.DeleteResponse;
import com.xcase.sharepoint.transputs.DownloadResponse;
import com.xcase.sharepoint.transputs.GetAccessTokenResponse;
import com.xcase.sharepoint.transputs.GetFileInfoResponse;
import com.xcase.sharepoint.transputs.GetFolderInfoResponse;
import com.xcase.sharepoint.transputs.UploadResponse;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SharepointResponseFactory extends BaseSharepointFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * create response object.
     *
     * @return response object
     */
    public static DeleteResponse createDeleteResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.DeleteResponse");
        return (DeleteResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static DownloadResponse createDownloadResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.DownloadResponse");
        return (DownloadResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetAccessTokenResponse createGetAccessTokenResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetAccessTokenResponse");
        return (GetAccessTokenResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFileInfoResponse createGetFileInfoResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetFileInfoResponse");
        return (GetFileInfoResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static GetFolderInfoResponse createGetFolderInfoResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.GetFolderInfoResponse");
        return (GetFolderInfoResponse) obj;
    }

    /**
     * create response object.
     *
     * @return response object
     */
    public static UploadResponse createUploadResponse() {
        Object obj = newInstanceOf("sharepoint4j.config.responsefactory.UploadResponse");
        return (UploadResponse) obj;
    }
}

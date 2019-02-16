/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint;

import com.xcase.common.impl.simple.core.ConfigurationManager;
import com.xcase.sharepoint.impl.simple.methods.DeleteMethod;
import com.xcase.sharepoint.impl.simple.methods.DownloadMethod;
import com.xcase.sharepoint.impl.simple.methods.GetAccessTokenMethod;
import com.xcase.sharepoint.impl.simple.methods.GetFileInfoMethod;
import com.xcase.sharepoint.impl.simple.methods.GetFolderInfoMethod;
import com.xcase.sharepoint.impl.simple.methods.UploadMethod;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.transputs.DeleteRequest;
import com.xcase.sharepoint.transputs.DeleteResponse;
import com.xcase.sharepoint.transputs.DownloadRequest;
import com.xcase.sharepoint.transputs.DownloadResponse;
import com.xcase.sharepoint.transputs.GetAccessTokenRequest;
import com.xcase.sharepoint.transputs.GetAccessTokenResponse;
import com.xcase.sharepoint.transputs.GetFileInfoRequest;
import com.xcase.sharepoint.transputs.GetFileInfoResponse;
import com.xcase.sharepoint.transputs.GetFolderInfoRequest;
import com.xcase.sharepoint.transputs.GetFolderInfoResponse;
import com.xcase.sharepoint.transputs.UploadRequest;
import com.xcase.sharepoint.transputs.UploadResponse;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class SimpleSharepointImpl implements SharepointExternalAPI {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    /**
     * configuration manager
     */
    public ConfigurationManager localConfigurationManager = ConfigurationManager.getConfigurationManager();

    /**
     * sharepoint action implementation.
     */
    private GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();

    /**
     * sharepoint action implementation.
     */
    private DeleteMethod deleteMethod = new DeleteMethod();

    /**
     * sharepoint action implementation.
     */
    private GetFileInfoMethod getFileInfoMethod = new GetFileInfoMethod();

    /**
     * sharepoint action implementation.
     */
    private GetFolderInfoMethod getFolderInfoMethod = new GetFolderInfoMethod();

    /**
     * sharepoint action implementation.
     */
    private DownloadMethod downloadMethod = new DownloadMethod();

    /**
     * sharepoint action implementation.
     */
    private UploadMethod uploadMethod = new UploadMethod();

    /**
     * @param getAccessTokenRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException sharepoint exception
     */
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) throws IOException, SharepointException {
        LOGGER.debug("starting getAccessToken()");
        return this.getAccessTokenMethod.getAccessToken(getAccessTokenRequest);
    }

    /**
     * @param uploadRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException sharepoint exception
     */
    public UploadResponse upload(UploadRequest uploadRequest) throws IOException, SharepointException {
        LOGGER.debug("starting upload()");
        return this.uploadMethod.upload(uploadRequest);
    }

    /**
     * @param downloadRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException sharepoint exception
     */
    public DownloadResponse download(DownloadRequest downloadRequest) throws IOException, SharepointException {
        return this.downloadMethod.download(downloadRequest);
    }

    /**
     * This method gets file info. 'file_id' param should contain valid id of
     * file.
     *
     * On successful a result, you will receive status 's_get_file_info' and
     * file info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getFileInfoRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException sharepoint exception
     */
    public GetFileInfoResponse getFileInfo(GetFileInfoRequest getFileInfoRequest) throws IOException, SharepointException {
        LOGGER.debug("starting getFileInfo()");
        return this.getFileInfoMethod.getFileInfo(getFileInfoRequest);
    }

    /**
     * This method gets folder info. 'folder_id' param should contain valid id of
     * folder.
     *
     * On successful a result, you will receive status 's_get_folder_info' and
     * folder info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getFolderInfoRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException sharepoint exception
     */
    public GetFolderInfoResponse getFolderInfo(GetFolderInfoRequest getFolderInfoRequest) throws IOException, SharepointException {
        return this.getFolderInfoMethod.getFolderInfo(getFolderInfoRequest);
    }

    /**
     * This method deletes a file or folder.
     *
     * 'target' param can be either 'file' or 'folder' depending on what you
     * want to delete, 'target_id' is id of a file or folder to be deleted.
     *
     * On a successful result, the status will be 's_delete_node'. If the result
     * wasn't successful, status field can be: 'e_delete_node', 'not_logged_in',
     * 'application_restricted'.
     *
     * @param deleteRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException sharepoint exception
     */
    public DeleteResponse delete(DeleteRequest deleteRequest) throws IOException, SharepointException {
        return this.deleteMethod.delete(deleteRequest);
    }
}

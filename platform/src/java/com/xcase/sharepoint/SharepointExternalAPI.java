/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.sharepoint;

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

/**
 * @author martin
 *
 */
public interface SharepointExternalAPI {

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
     * @param deleteRequest request object
     * @return response object
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    DeleteResponse delete(DeleteRequest deleteRequest) throws IOException,
            SharepointException;

    /**
	 *
	 * @param getAccessTokenRequest request object
	 * @return getAccessTokenResponse response object
	 * @throws IOException io exception
	 * @throws SharepointException sharepoint exception
	 */
	GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) throws IOException, SharepointException;

    /**
     * This method gets file info. 'file_id' param should contain valid id of
     * file.
     *
     * On successful a result, you will receive status 's_get_file_info' and
     * file info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getFileInfoRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SharepointException SharePoint exception
     */
    GetFileInfoResponse getFileInfo(GetFileInfoRequest getFileInfoRequest) throws IOException, SharepointException;

    /**
     * This method gets folder info. 'folder_id' param should contain valid id of
     * folder.
     *
     * On successful a result, you will receive status 's_get_file_info' and
     * file info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getFolderInfoRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SharepointException SharePoint exception
     */
    GetFolderInfoResponse getFolderInfo(GetFolderInfoRequest getFolderInfoRequest) throws IOException, SharepointException;

    /**
     * download a file.
     *
     * @param downloadRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SharepointException box exception
     */
    DownloadResponse download(DownloadRequest downloadRequest) throws IOException, SharepointException;

    /**
     * upload files.
     *
     * @param uploadRequest request object
     * @return response object
     * @throws IOException IO exception
     * @throws SharepointException box exception
     */
    UploadResponse upload(UploadRequest uploadRequest) throws IOException, SharepointException;

}

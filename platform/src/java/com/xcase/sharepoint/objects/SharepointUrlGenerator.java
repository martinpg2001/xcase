/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.objects;

/**
 *
 * @author martin
 */
public interface SharepointUrlGenerator {

    public String getSharepointGetFilesUrl(String serverUrl, String site, String escapedDirectoryId);

    public String getSharepointGetFileUrl(String serverUrl, String site, String escapedDirectoryId, String fileId);

    public String getSharepointGetFoldersUrl(String serverUrl, String site, String escapedDirectoryId);
}

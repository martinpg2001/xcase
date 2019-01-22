/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.objects;

import com.xcase.sharepoint.objects.SharepointUrlGenerator;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class Sharepoint2013UrlGenerator implements SharepointUrlGenerator {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public String getSharepointGetFilesUrl(String serverUrl, String site, String escapedDirectoryId) {
        LOGGER.debug("starting getSharePointGetFilesUrl()");
        return serverUrl + "/" + site + "/_api/web/GetFolderByServerRelativeUrl('" + escapedDirectoryId + "')/Files";
    }

    public String getSharepointGetFileUrl(String serverUrl, String site, String escapedDirectoryId, String fileId) {
        LOGGER.debug("starting getSharePointGetFileUrl()");
        return serverUrl + "/" + site + "/_api/web/GetFolderByServerRelativeUrl('" + escapedDirectoryId + "')/Files";
    }

    public String getSharepointGetFoldersUrl(String serverUrl, String site, String escapedDirectoryId) {
        LOGGER.debug("starting getSharepointGetFoldersUrl()");
        return serverUrl + "/" + site + "/_api/web/GetFolderByServerRelativeUrl('" + escapedDirectoryId + "')/Folders";
    }
}

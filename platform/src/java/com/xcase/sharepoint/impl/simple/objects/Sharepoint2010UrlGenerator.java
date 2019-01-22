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
public class Sharepoint2010UrlGenerator implements SharepointUrlGenerator {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public String getSharepointGetFilesUrl(String serverUrl, String site, String escapedDirectoryId) {
        LOGGER.debug("starting getSharepointGetFilesUrl()");
        return serverUrl + "/" + site + "/_vti_bin/listdata.svc/SharedDocuments";
    }

    public String getSharepointGetFileUrl(String serverUrl, String site, String escapedDirectoryId, String fileId) {
        LOGGER.debug("starting getSharepointGetFileUrl()");
        return serverUrl + "/" + site + "/_api/web/GetFileByServerRelativeUrl('/" + site + "/" + escapedDirectoryId + "/" + fileId + "')/$value";
    }

    public String getSharepointGetFoldersUrl(String serverUrl, String site, String escapedDirectoryId) {
        LOGGER.debug("starting getSharepointGetFoldersUrl()");
        return serverUrl + "/" + site + "/_vti_bin/listdata.svc/SharedDocuments";
    }
}

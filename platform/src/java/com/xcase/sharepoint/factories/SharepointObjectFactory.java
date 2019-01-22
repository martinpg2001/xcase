/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.factories;

import com.xcase.sharepoint.objects.SharepointFile;
import com.xcase.sharepoint.objects.UploadResult;
import com.xcase.sharepoint.objects.SharepointUrlGenerator;
import com.xcase.sharepoint.objects.SharepointSubscription;
import com.xcase.sharepoint.objects.SharepointFriend;
import com.xcase.sharepoint.objects.SharepointTag;
import com.xcase.sharepoint.objects.SharepointAbstractFile;
import com.xcase.sharepoint.objects.SharepointUser;
import com.xcase.sharepoint.objects.Sharepoint;
import com.xcase.sharepoint.objects.SharepointFolder;

/**
 *
 * @author martin
 */
public class SharepointObjectFactory extends BaseSharepointFactory {

    /**
     * create a BoxFile object.
     *
     * @return BoxFile object.
     */
    public static SharepointFile createSharepointFile() {
        Object obj = newInstanceOf("sharepoint4j.config.objectfactory.SharepointFile");
        return (SharepointFile) obj;
    }

    /**
     * create a BoxFolder object.
     *
     * @return BoxFolder object.
     */
    public static SharepointFolder createSharepointFolder() {
        Object obj = newInstanceOf("sharepoint4j.config.objectfactory.SharepointFolder");
        return (SharepointFolder) obj;
    }

    /**
     * create a BoxFriend object.
     *
     * @return BoxFriend object.
     */
    public static SharepointFriend createSharepointFriend() {
        Object obj = newInstanceOf("sharepoint4j.config.objectfactory.SharepointFriend");
        return (SharepointFriend) obj;
    }

    /**
     * create a Box object.
     *
     * @return Box object.
     */
    public static Sharepoint createSharepoint() {
        Object obj = newInstanceOf("sharepoint4j.config.objectfactory.Sharepoint");
        return (Sharepoint) obj;
    }

    /**
     * create a BoxSubscription object.
     *
     * @return BoxSubscription object.
     */
    public static SharepointSubscription createSharepointSubscription() {
        Object obj = newInstanceOf("sharepoint4j.config.objectfactory.SharepointSubscription");
        return (SharepointSubscription) obj;
    }

    /**
     * create a BoxTag object.
     *
     * @return BoxTag object.
     */
    public static SharepointTag createSharepointTag() {
        Object obj = newInstanceOf("sharepoint4j.config.objectfactory.SharepointTag");
        return (SharepointTag) obj;
    }

    /**
     * create a BoxUser object.
     *
     * @return BoxUser object.
     */
    public static SharepointUser createSharepointUser() {
        Object obj = newInstanceOf("sharepoint4j.config.objectfactory.SharepointUser");
        return (SharepointUser) obj;
    }

    /**
     * create a UploadResult object.
     *
     * @return UploadResult object.
     */
    public static UploadResult createUploadResult() {
        Object obj = newInstanceOf("sharepoint4j.config.objectfactory.UploadResult");
        return (UploadResult) obj;
    }

    /**
     * create a BoxAbstractFile object.
     *
     * @return BoxAbstractFile object.
     */
    public static SharepointAbstractFile createSharepointAbstractFile() {
        Object obj = newInstanceOf("sharepoint4j.config.objectfactory.SharepointAbstractFile");
        return (SharepointAbstractFile) obj;
    }

    public static SharepointUrlGenerator createSharepointUrlGenerator() {
        LOGGER.debug("starting createSharepointUrlGenerator()");
        Object obj = newInstanceOf("sharepoint4j.config.objectfactory.SharepointUrlGenerator");
        return (SharepointUrlGenerator) obj;
    }
}

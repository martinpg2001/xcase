/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.factories;

import com.xcase.box.objects.BoxMembership;
import com.xcase.box.objects.BoxAbstractFile;
import com.xcase.box.objects.BoxTag;
import com.xcase.box.objects.UploadResult;
import com.xcase.box.objects.Box;
import com.xcase.box.objects.BoxCollaboration;
import com.xcase.box.objects.BoxUser;
import com.xcase.box.objects.BoxFriend;
import com.xcase.box.objects.BoxFolder;
import com.xcase.box.objects.BoxFile;
import com.xcase.box.objects.BoxSubscription;
import com.xcase.box.objects.BoxGroup;
import com.xcase.box.objects.BoxAccessibleBy;

/**
 *
 * @author martin
 */
public class BoxObjectFactory extends BaseBoxFactory {

    /**
     * create a BoxAccessibleBy object.
     *
     * @return BoxAccessibleBy object.
     */
    public static BoxAccessibleBy createBoxAccessibleBy() {
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxAccessibleBy");
        return (BoxAccessibleBy) obj;
    }

    /**
     * create a BoxCollaboration object.
     *
     * @return BoxCollaboration object.
     */
    public static BoxCollaboration createBoxCollaboration() {
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxCollaboration");
        return (BoxCollaboration) obj;
    }

    /**
     * create a BoxFile object.
     *
     * @return BoxFile object.
     */
    public static BoxFile createBoxFile() {
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxFile");
        return (BoxFile) obj;
    }

    /**
     * create a BoxFolder object.
     *
     * @return BoxFolder object.
     */
    public static BoxFolder createBoxFolder() {
        LOGGER.debug("starting createBoxFolder()");
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxFolder");
        return (BoxFolder) obj;
    }

    /**
     * create a BoxFriend object.
     *
     * @return BoxFriend object.
     */
    public static BoxFriend createBoxFriend() {
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxFriend");
        return (BoxFriend) obj;
    }

    /**
     * create a Box object.
     *
     * @return Box object.
     */
    public static Box createBox() {
        Object obj = newInstanceOf("box4j.config.objectfactory.Box");
        return (Box) obj;
    }

    /**
     * create a BoxGroup object.
     *
     * @return BoxGroup object.
     */
    public static BoxGroup createBoxGroup() {
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxGroup");
        return (BoxGroup) obj;
    }

    /**
     * create a BoxMembership object.
     *
     * @return BoxMembership object.
     */
    public static BoxMembership createBoxMembership() {
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxMembership");
        return (BoxMembership) obj;
    }

    /**
     * create a BoxSubscription object.
     *
     * @return BoxSubscription object.
     */
    public static BoxSubscription createBoxSubscription() {
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxSubscription");
        return (BoxSubscription) obj;
    }

    /**
     * create a BoxTag object.
     *
     * @return BoxTag object.
     */
    public static BoxTag createBoxTag() {
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxTag");
        return (BoxTag) obj;
    }

    /**
     * create a BoxUser object.
     *
     * @return BoxUser object.
     */
    public static BoxUser createBoxUser() {
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxUser");
        return (BoxUser) obj;
    }

    /**
     * create a UploadResult object.
     *
     * @return UploadResult object.
     */
    public static UploadResult createUploadResult() {
        Object obj = newInstanceOf("box4j.config.objectfactory.UploadResult");
        return (UploadResult) obj;
    }

    /**
     * create a BoxAbstractFile object.
     *
     * @return BoxAbstractFile object.
     */
    public static BoxAbstractFile createBoxAbstractFile() {
        Object obj = newInstanceOf("box4j.config.objectfactory.BoxAbstractFile");
        return (BoxAbstractFile) obj;
    }
}

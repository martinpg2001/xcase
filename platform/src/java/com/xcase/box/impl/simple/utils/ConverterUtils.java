/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.utils;

import com.xcase.box.objects.BoxFriend;
import com.xcase.box.objects.BoxFolder;
import com.xcase.box.objects.BoxMembership;
import com.xcase.box.objects.BoxAbstractFile;
import com.xcase.box.objects.BoxTag;
import com.xcase.box.objects.Box;
import com.xcase.box.objects.BoxFile;
import com.xcase.box.objects.BoxCollaboration;
import com.xcase.box.objects.BoxSubscription;
import com.xcase.box.objects.BoxUser;
import com.xcase.box.objects.BoxGroup;
import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxObjectFactory;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.*;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class ConverterUtils {

    /**
     * log4j object.
     */
    static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * convert tags element to tag list.
     *
     * @param tagsElm tags element
     * @return object list
     */
    public static List transferTags2List(Element tagsElm) {
        List tagsList = new ArrayList();
        for (int i = 0; i < tagsElm.nodeCount(); i++) {
            Element tagElm = (Element) tagsElm.node(i);
            String id = tagElm.attributeValue("id");
            String name = tagElm.getText();
            BoxTag tag = BoxObjectFactory.createBoxTag();
            tag.setId(id);
            tag.setName(name);
            tagsList.add(tag);
        }

        return tagsList;
    }

    /**
     *
     * @param fileJsonObject
     * @return object
     */
    public static BoxAbstractFile toBoxAbstractFile(JsonObject fileJsonObject) {
        LOGGER.debug("starting toBoxAbstractFile()");
        BoxAbstractFile boxAbstractFile = BoxObjectFactory.createBoxAbstractFile();
        LOGGER.debug("created boxAbstractFile");
        String type = fileJsonObject.get("type").getAsString();
        LOGGER.debug("type is " + type);
        boxAbstractFile.setFolder("folder".equalsIgnoreCase(type));
        boxAbstractFile.setId(fileJsonObject.get("id").getAsString());
        LOGGER.debug("got id element");
        boxAbstractFile.setName(fileJsonObject.get("name").getAsString());
        LOGGER.debug("finishing toBoxAbstractFile()");
        return boxAbstractFile;
    }

    /**
     *
     * @param collaborationJsonObject
     * @return object
     */
    public static BoxCollaboration toBoxCollaboration(JsonObject collaborationJsonObject) {
        LOGGER.debug("starting toBoxCollaboration()");
        BoxCollaboration boxCollaboration = BoxObjectFactory.createBoxCollaboration();
        LOGGER.debug("created boxCollaboration");
        boxCollaboration.setId(collaborationJsonObject.get("id").getAsString());
        boxCollaboration.setRole(collaborationJsonObject.get("role").getAsString());
        LOGGER.debug("finishing toBoxCollaboration()");
        return boxCollaboration;
    }

    /**
     *
     * @param folderJsonObject
     * @return object
     */
    public static BoxFolder toBoxFolder(JsonObject folderJsonObject) {
        LOGGER.debug("starting toBoxFolder()");
        BoxFolder boxFolder = BoxObjectFactory.createBoxFolder();
        LOGGER.debug("created Box folder()");
        //JsonObject folderIdElm = (JsonObject)folderJsonObject.get(BoxConstant.PARAM_NAME_FOLDER_ID);
        JsonPrimitive folderIdElm = (JsonPrimitive) folderJsonObject.get("id");
        LOGGER.debug("got folder ID element");
        //JsonObject folderNameElm = (JsonObject)folderJsonObject.get(BoxConstant.PARAM_NAME_FOLDER_NAME);
        JsonPrimitive folderNameElm = (JsonPrimitive) folderJsonObject.get("name");
        LOGGER.debug("got folder name element");
        JsonObject folderTypeIdElm = (JsonObject) folderJsonObject.get(BoxConstant.PARAM_NAME_FOLDER_TYPE_ID);
        LOGGER.debug("got folder type ID element");
        //JsonObject parentFolderIdElm = (JsonObject)folderJsonObject.get(BoxConstant.PARAM_NAME_PARENT_FOLDER_ID);
        JsonObject parentFolderIdElm = (JsonObject) folderJsonObject.get("parent");
        LOGGER.debug("got parent folder ID element");
        JsonObject userIdElm = (JsonObject) folderJsonObject.get(BoxConstant.PARAM_NAME_USER_ID);
        LOGGER.debug("got user ID element");
        JsonObject pathElm = (JsonObject) folderJsonObject.get("path_collection");
        LOGGER.debug("got folder path element");
        JsonObject sharedElm = (JsonObject) folderJsonObject.get(BoxConstant.PARAM_NAME_SHARED);
        LOGGER.debug("got shared element");
        JsonObject publicNameElm = (JsonObject) folderJsonObject.get(BoxConstant.PARAM_NAME_PUBLIC_NAME);
        LOGGER.debug("got public name element");
        JsonObject showCommentsElm = (JsonObject) folderJsonObject.get(BoxConstant.PARAM_NAME_SHOW_COMMENTS);
        LOGGER.debug("got show comments element");
        JsonObject passwordElm = (JsonObject) folderJsonObject.get(BoxConstant.PARAM_NAME_PASSWORD);
        LOGGER.debug("got password element");
        if (folderIdElm != null) {
            String folderID = folderIdElm.getAsString();
            LOGGER.debug("folderID is " + folderID);
            boxFolder.setFolderId(folderID);
            LOGGER.debug("set folder ID as String");
        }

        if (folderNameElm != null) {
            boxFolder.setFolderName(folderNameElm.getAsString());
            LOGGER.debug("set folder name as String");
        }

        if (folderTypeIdElm != null) {
            boxFolder.setFolderTypeId(folderTypeIdElm.getAsString());
        }

        if (parentFolderIdElm != null) {
            LOGGER.debug("parentFolderIdElm is not null");
            String parentFolderId = parentFolderIdElm.getAsJsonPrimitive("id").getAsString();
            LOGGER.debug("parentFolderId is " + parentFolderId);
            boxFolder.setParentFolderId(parentFolderId);
            LOGGER.debug("set parentFolderId to " + parentFolderId);
        }

        if (passwordElm != null) {
            LOGGER.debug("passwordElm is not null");
            boxFolder.setPassword(passwordElm.getAsString());
        }

        if (pathElm != null) {
            LOGGER.debug("pathElm is not null");
            JsonArray entriesArray = pathElm.getAsJsonArray("entries");
            String path = "";
            for (int i = 0; i < entriesArray.size(); i++) {
                String name = ((JsonObject) entriesArray.get(i)).get("name").getAsString();
                LOGGER.debug("name is " + name);
                path = path + "/" + name;
            }

            boxFolder.setPath(path);
        }

        if (publicNameElm != null) {
            LOGGER.debug("publicNameElm is not null");
            boxFolder.setPublicName(publicNameElm.getAsString());
        }
        if (sharedElm != null) {
            LOGGER.debug("sharedElm is not null");
            boxFolder.setShared(sharedElm.getAsString());
        }
        if (showCommentsElm != null) {
            LOGGER.debug("showCommentsElm is not null");
            boxFolder.setShowComments(showCommentsElm.getAsString());
        }
        if (userIdElm != null) {
            LOGGER.debug("userIdElm is not null");
            boxFolder.setUserId(userIdElm.getAsString());
        }

        LOGGER.debug("finishing toBoxFolder()");
        return boxFolder;
    }

    /**
     *
     * @param folderElm folder element
     * @return object
     */
    public static BoxFolder toBoxFolder(Element folderElm) {
        BoxFolder boxFolder = BoxObjectFactory.createBoxFolder();
        Element folderIdElm = folderElm.element(BoxConstant.PARAM_NAME_FOLDER_ID);
        Element folderNameElm = folderElm.element(BoxConstant.PARAM_NAME_FOLDER_NAME);
        Element folderTypeIdElm = folderElm.element(BoxConstant.PARAM_NAME_FOLDER_TYPE_ID);
        Element parentFolderIdElm = folderElm.element(BoxConstant.PARAM_NAME_PARENT_FOLDER_ID);
        Element userIdElm = folderElm.element(BoxConstant.PARAM_NAME_USER_ID);
        Element pathElm = folderElm.element(BoxConstant.PARAM_NAME_PATH);
        Element sharedElm = folderElm.element(BoxConstant.PARAM_NAME_SHARED);
        Element publicNameElm = folderElm.element(BoxConstant.PARAM_NAME_PUBLIC_NAME);
        Element showCommentsElm = folderElm.element(BoxConstant.PARAM_NAME_SHOW_COMMENTS);
        Element passwordElm = folderElm.element(BoxConstant.PARAM_NAME_PASSWORD);
        boxFolder.setFolderId(folderIdElm.getText());
        boxFolder.setFolderName(folderNameElm.getText());
        if (folderTypeIdElm != null) {
            boxFolder.setFolderTypeId(folderTypeIdElm.getText());
        }
        boxFolder.setParentFolderId(parentFolderIdElm.getText());
        if (passwordElm != null) {
            boxFolder.setPassword(passwordElm.getText());
        }
        if (pathElm != null) {
            boxFolder.setPath(pathElm.getText());
        }
        if (publicNameElm != null) {
            boxFolder.setPublicName(publicNameElm.getText());
        }
        if (sharedElm != null) {
            boxFolder.setShared(sharedElm.getText());
        }
        if (showCommentsElm != null) {
            boxFolder.setShowComments(showCommentsElm.getText());
        }
        if (userIdElm != null) {
            boxFolder.setUserId(userIdElm.getText());
        }

        return boxFolder;
    }

    /**
     *
     * @param fileJsonObject
     * @return object
     */
    public static BoxFile toBoxFile(JsonObject fileJsonObject) {
        LOGGER.debug("starting toBoxFile()");
        BoxFile boxFile = BoxObjectFactory.createBoxFile();
        LOGGER.debug("created boxFile");
        boxFile.setFileId(fileJsonObject.get("id").getAsString());
        boxFile.setFileName(fileJsonObject.get("name").getAsString());
        LOGGER.debug("finishing toBoxFile()");
        return boxFile;
    }

    /**
     * @param infoElm info element
     * @return object
     */
    public static BoxFile toBoxFile(Element infoElm) {
        BoxFile boxFile = BoxObjectFactory.createBoxFile();
        Element fileIdElm = infoElm.element(BoxConstant.PARAM_NAME_FILE_ID);
        Element fileNameElm = infoElm.element(BoxConstant.PARAM_NAME_FILE_NAME);
        Element folderIdElm = infoElm.element(BoxConstant.PARAM_NAME_FOLDER_ID);
        Element sharedElm = infoElm.element(BoxConstant.PARAM_NAME_SHARED);
        Element sharedNameElm = infoElm.element(BoxConstant.PARAM_NAME_SHARED_NAME);
        Element sizeElm = infoElm.element(BoxConstant.PARAM_NAME_SIZE);
        Element descriptionElm = infoElm.element(BoxConstant.PARAM_NAME_DESCRIPTION);
        Element sha1Elm = infoElm.element(BoxConstant.PARAM_NAME_SHA1);
        Element createdElm = infoElm.element(BoxConstant.PARAM_NAME_CREATED);
        Element updatedElm = infoElm.element(BoxConstant.PARAM_NAME_UPDATED);
        if (fileIdElm != null) {
            boxFile.setFileId(fileIdElm.getText());
        }

        if (fileNameElm != null) {
            boxFile.setFileName(fileNameElm.getText());
        }

        if (folderIdElm != null) {
            boxFile.setFolderId(folderIdElm.getText());
        }

        if (sharedElm != null) {
            String sharedStr = sharedElm.getText();
            if ("0".equals(sharedStr)) {
                boxFile.setShared(false);
            } else {
                boxFile.setShared(true);
            }
        }

        if (sharedNameElm != null) {
            boxFile.setSharedName(sharedNameElm.getText());
        }

        if (sizeElm != null) {
            long size = 0;
            String sizeStr = sizeElm.getText();
            try {
                size = Long.parseLong(sizeStr);
            } catch (NumberFormatException e) {
                size = Long.MIN_VALUE;
            }
            boxFile.setSize(size);
        }

        if (descriptionElm != null) {
            boxFile.setDescription(descriptionElm.getText());
        }

        if (sha1Elm != null) {
            boxFile.setSha1(sha1Elm.getText());
        }

        if (createdElm != null) {
            long created = 0;
            String createdStr = createdElm.getText();
            try {
                created = Long.parseLong(createdStr);
            } catch (NumberFormatException e) {
                created = Long.MIN_VALUE;
            }

            boxFile.setCreated(created);
        }

        if (updatedElm != null) {
            long updated = 0;
            String updatedStr = updatedElm.getText();
            try {
                updated = Long.parseLong(updatedStr);
            } catch (NumberFormatException e) {
                updated = Long.MIN_VALUE;
            }

            boxFile.setUpdated(updated);
        }

        return boxFile;
    }

    /**
     *
     * @param groupJsonObject user JsonObject
     * @return object
     */
    public static BoxGroup toBoxGroup(JsonObject groupJsonObject) {
        LOGGER.debug("starting toBoxGroup()");
        BoxGroup boxGroup = BoxObjectFactory.createBoxGroup();
        LOGGER.info("created boxGroup");
        boxGroup.setId(groupJsonObject.get("id").getAsString());
        LOGGER.info("set id to " + groupJsonObject.get("id").getAsString());
        boxGroup.setName(groupJsonObject.get("name").getAsString());
        LOGGER.debug("finishing toBoxGroup()");
        return boxGroup;
    }

    /**
     *
     * @param groupArrayJsonObject user JsonObject
     * @return object list
     */
    public static List<BoxGroup> toBoxGroupList(JsonArray groupArrayJsonObject) {
        LOGGER.debug("starting toBoxGroupList()");
        List<BoxGroup> boxGroupList = new ArrayList<BoxGroup>();
        LOGGER.info("created boxGroupList");
        Iterator<JsonElement> boxGroupIterator = groupArrayJsonObject.iterator();
        while (boxGroupIterator.hasNext()) {
            JsonObject boxGroupJsonObject = boxGroupIterator.next().getAsJsonObject();
            BoxGroup boxGroup = toBoxGroup(boxGroupJsonObject);
            boxGroupList.add(boxGroup);
        }

        LOGGER.debug("finishing toBoxGroupList()");
        return boxGroupList;
    }

    /**
     *
     * @param membershipJsonObject
     * @return object
     */
    public static BoxMembership toBoxMembership(JsonObject membershipJsonObject) {
        LOGGER.debug("starting toBoxMembership()");
        BoxMembership boxMembership = BoxObjectFactory.createBoxMembership();
        LOGGER.debug("created boxMembership");
        boxMembership.setId(membershipJsonObject.get("id").getAsString());
        LOGGER.debug("finishing toBoxMembership()");
        return boxMembership;
    }

    /**
     *
     * @param membershipArrayJsonObject
     * @return object list
     */
    public static List<BoxMembership> toBoxMembershipList(JsonArray membershipArrayJsonObject) {
        LOGGER.debug("starting toBoxMembershipList()");
        List<BoxMembership> boxMembershipList = new ArrayList<BoxMembership>();
        LOGGER.info("created boxMembershipList");
        Iterator<JsonElement> boxMembershipIterator = membershipArrayJsonObject.iterator();
        while (boxMembershipIterator.hasNext()) {
            JsonObject boxMembershipJsonObject = boxMembershipIterator.next().getAsJsonObject();
            BoxMembership boxMembership = toBoxMembership(boxMembershipJsonObject);
            boxMembershipList.add(boxMembership);
        }

        LOGGER.debug("finishing toBoxMembershipList()");
        return boxMembershipList;
    }

    /**
     *
     * @param userJsonObject user JsonObject
     * @return object
     */
    public static BoxUser toBoxUser(JsonObject userJsonObject) {
        LOGGER.debug("starting toBoxUser()");
        BoxUser boxUser = BoxObjectFactory.createBoxUser();
        LOGGER.debug("created boxUser");
        boxUser.setUserId(userJsonObject.get("id").getAsString());
        boxUser.setLogin(userJsonObject.get("login").getAsString());
        boxUser.setName(userJsonObject.get("name").getAsString());
        LOGGER.debug("finishing toBoxUser()");
        return boxUser;
    }

    /**
     * @param friendsElm friends element
     * @return object list
     */
    public static List toFriendsList(Element friendsElm) {
        List friendsList = new ArrayList();
        if (friendsElm != null) {
            for (int i = 0; i < friendsElm.nodeCount(); i++) {
                BoxFriend friend = BoxObjectFactory.createBoxFriend();
                Element friendElm = (Element) friendsElm.node(i);

                Element nameElm = friendElm.element("name");
                Element emailElm = friendElm.element("email");
                Element acceptedElm = friendElm.element("accepted");
                Element avatarUrlElm = friendElm.element("avatar_url");
                Element boxesElm = friendElm.element("boxes");
                Element subscriptionsElm = friendElm.element("subscriptions");

                if (nameElm != null) {
                    friend.setName(nameElm.getText());
                }
                if (emailElm != null) {
                    friend.setEmail(emailElm.getText());
                }
                if (acceptedElm != null) {
                    friend.setAccepted(acceptedElm.getText());
                }
                if (avatarUrlElm != null) {
                    friend.setAvatarUrl(avatarUrlElm.getText());
                }
                List boxList = new ArrayList();
                if (boxesElm != null) {
                    for (int j = 0; j < boxesElm.nodeCount(); j++) {
                        Element boxElm = (Element) boxesElm.node(j);
                        Box box = toBox(boxElm);
                        boxList.add(box);
                    }
                }
                friend.setBoxes(boxList);

                List subscriptionList = new ArrayList();
                if (subscriptionsElm != null) {
                    for (int j = 0; j < subscriptionsElm.nodeCount(); j++) {
                        Element subscriptionElm = (Element) subscriptionsElm
                                .node(j);
                        BoxSubscription subscription = toSubscription(subscriptionElm);
                        subscriptionList.add(subscription);
                    }
                }
                friend.setSubscriptions(subscriptionList);

                friendsList.add(friend);
            }
        }
        return friendsList;
    }

    /**
     *
     * @param boxElm box element
     * @return object
     */
    public static Box toBox(Element boxElm) {
        Box box = BoxObjectFactory.createBox();
        Element idElm = boxElm.element("id");
        Element urlElm = boxElm.element("url");
        Element statusElm = boxElm.element("status");
        if (idElm != null) {
            box.setId(idElm.getText());
        }

        if (urlElm != null) {
            box.setUrl(urlElm.getText());
        }

        if (statusElm != null) {
            box.setStatus(statusElm.getText());
        }

        return box;
    }

    /**
     *
     * @param subscriptionElm subscription element
     * @return object
     */
    public static BoxSubscription toSubscription(Element subscriptionElm) {
        BoxSubscription subscription = BoxObjectFactory.createBoxSubscription();
        Element boxIdElm = subscriptionElm.element("box_id");
        Element userNameElm = subscriptionElm.element("user_name");
        Element urlElm = subscriptionElm.element("url");
        Element statusElm = subscriptionElm.element("status");
        if (boxIdElm != null) {
            subscription.setBoxId(boxIdElm.getText());
        }

        if (userNameElm != null) {
            subscription.setUserName(userNameElm.getText());
        }

        if (urlElm != null) {
            subscription.setUrl(urlElm.getText());
        }

        if (statusElm != null) {
            subscription.setStatus(statusElm.getText());
        }

        return subscription;
    }

    /**
     *
     */
    private ConverterUtils() {
    }
}

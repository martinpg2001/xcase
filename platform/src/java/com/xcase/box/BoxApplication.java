/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box;

import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxMembership;
import com.xcase.box.objects.BoxGroup;
import com.xcase.box.impl.simple.core.BoxConfigurationManager;
import com.xcase.box.factories.BoxRequestFactory;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.CreateAuthorizationCodeRequest;
import com.xcase.box.transputs.CreateAuthorizationCodeResponse;
import com.xcase.box.transputs.CreateCollaborationRequest;
import com.xcase.box.transputs.CreateCollaborationResponse;
import com.xcase.box.transputs.CreateFolderRequest;
import com.xcase.box.transputs.CreateFolderResponse;
import com.xcase.box.transputs.CreateGroupRequest;
import com.xcase.box.transputs.CreateGroupResponse;
import com.xcase.box.transputs.CreateMembershipRequest;
import com.xcase.box.transputs.CreateMembershipResponse;
import com.xcase.box.transputs.CreateUserRequest;
import com.xcase.box.transputs.CreateUserResponse;
import com.xcase.box.transputs.DeleteCollaborationRequest;
import com.xcase.box.transputs.DeleteCollaborationResponse;
import com.xcase.box.transputs.DeleteGroupRequest;
import com.xcase.box.transputs.DeleteGroupResponse;
import com.xcase.box.transputs.DeleteMembershipRequest;
import com.xcase.box.transputs.DeleteMembershipResponse;
import com.xcase.box.transputs.DeleteUserRequest;
import com.xcase.box.transputs.DeleteUserResponse;
import com.xcase.box.transputs.GetAccessTokenRequest;
import com.xcase.box.transputs.GetAccessTokenResponse;
import com.xcase.box.transputs.GetCollaborationRequest;
import com.xcase.box.transputs.GetCollaborationResponse;
import com.xcase.box.transputs.GetFolderInfoRequest;
import com.xcase.box.transputs.GetFolderInfoResponse;
import com.xcase.box.transputs.GetGroupRequest;
import com.xcase.box.transputs.GetGroupResponse;
import com.xcase.box.transputs.GetGroupsForUserRequest;
import com.xcase.box.transputs.GetGroupsForUserResponse;
import com.xcase.box.transputs.GetMembershipRequest;
import com.xcase.box.transputs.GetMembershipResponse;
import com.xcase.box.transputs.GetMembershipsForGroupRequest;
import com.xcase.box.transputs.GetMembershipsForGroupResponse;
import com.xcase.box.transputs.GetMembershipsForUserRequest;
import com.xcase.box.transputs.GetMembershipsForUserResponse;
import com.xcase.box.transputs.GetUserInfoRequest;
import com.xcase.box.transputs.GetUserInfoResponse;
import com.xcase.box.transputs.LogoutRequest;
import com.xcase.box.transputs.RefreshAccessTokenRequest;
import com.xcase.box.transputs.RefreshAccessTokenResponse;
import com.xcase.box.transputs.UpdateCollaborationRequest;
import com.xcase.box.transputs.UpdateCollaborationResponse;
import com.xcase.box.transputs.UpdateGroupRequest;
import com.xcase.box.transputs.UpdateGroupResponse;
import com.xcase.box.transputs.UpdateMembershipRequest;
import com.xcase.box.transputs.UpdateMembershipResponse;
import com.xcase.box.transputs.UpdateUserRequest;
import com.xcase.box.transputs.UpdateUserResponse;
import java.io.*;
import java.lang.invoke.*;
import java.util.List;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class BoxApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        /**
         * Client information from local configuration file
         */
        String clientID = BoxConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(BoxConstant.LOCAL_OAUTH2_CLIENT_ID);
        LOGGER.debug("clientID is " + clientID);
        String clientSecret = BoxConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(BoxConstant.LOCAL_OAUTH2_CLIENT_SECRET);
        LOGGER.debug("clientSecret is " + clientSecret);
        String username = BoxConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(BoxConstant.LOCAL_OAUTH2_USERNAME);
        LOGGER.debug("username is " + username);
        String password = BoxConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(BoxConstant.LOCAL_OAUTH2_PASSWORD);
        LOGGER.debug("password is " + password);
        String redirectURI = BoxConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(BoxConstant.LOCAL_OAUTH2_REDIRECT_URI);
        LOGGER.debug("redirectURI is " + redirectURI);
        String refreshToken = BoxConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(BoxConstant.LOCAL_OAUTH2_REFRESH_TOKEN);
        LOGGER.debug("refreshToken is " + refreshToken);
        BoxExternalAPI boxExternalAPI = new SimpleBoxImpl();
        LOGGER.debug("created boxExternalAPI");
        try {
            // Create authorization code
            LOGGER.debug("about to create authorization code");
            CreateAuthorizationCodeRequest createAuthorizationCodeRequest = BoxRequestFactory.createCreateAuthorizationCodeRequest(clientID, username, password, redirectURI);
            LOGGER.debug("created createAuthorizationCodeRequest");
            CreateAuthorizationCodeResponse createAuthorizationCodeResponse = boxExternalAPI.createAuthorizationCode(createAuthorizationCodeRequest);
            LOGGER.debug("created authorization code");
            String authorizationCode = createAuthorizationCodeResponse.getAuthorizationCode();
            LOGGER.debug("authorizationCode is " + authorizationCode);

            // Get access token
            GetAccessTokenRequest getAccessTokenRequest = BoxRequestFactory.createGetAccessTokenRequest(clientID, authorizationCode, clientSecret);
            LOGGER.debug("created getAccessTokenRequest");
            GetAccessTokenResponse getAccessTokenResponse = boxExternalAPI.getAccessToken(getAccessTokenRequest);
            LOGGER.debug("got access token");
            String accessToken = getAccessTokenResponse.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);

            // Refresh access toke
            LOGGER.debug("about to refresh access token");
            RefreshAccessTokenRequest refreshAccessTokenRequest = BoxRequestFactory.createRefreshAccessTokenRequest(clientID, refreshToken, clientSecret);
            LOGGER.debug("created refreshAccessTokenRequest");
            RefreshAccessTokenResponse refreshAccessTokenResponse = boxExternalAPI.refreshAccessToken(refreshAccessTokenRequest);
            LOGGER.debug("refreshed access token");
            accessToken = refreshAccessTokenResponse.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            refreshToken = refreshAccessTokenResponse.getRefreshToken();
            LOGGER.debug("refreshToken is " + refreshToken);
            BoxConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(BoxConstant.LOCAL_OAUTH2_REFRESH_TOKEN, refreshToken);
            BoxConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
            LOGGER.debug("stored config properties");
            LOGGER.debug("accessToken is " + accessToken);

            // create a folder
            CreateFolderRequest createFolderRequest = BoxRequestFactory.createCreateFolderRequest(accessToken, "0", "folderName" + System.currentTimeMillis(), false);
            CreateFolderResponse createFolderResponse = boxExternalAPI.createFolder(createFolderRequest);
            String createdFolderId = createFolderResponse.getFolder().getFolderId();
            LOGGER.debug("createdFolderId is " + createdFolderId);

            // get folder info
            GetFolderInfoRequest getFolderInfoRequest = BoxRequestFactory.createGetFolderInfoRequest(accessToken, createdFolderId);
            GetFolderInfoResponse getFolderInfoResponse = boxExternalAPI.getFolderInfo(getFolderInfoRequest);
            String getFolderInfoId = getFolderInfoResponse.getFolder().getFolderId();
            LOGGER.debug("getFolderInfoId is " + getFolderInfoId);

            // create a temp file for upload
//            String fileName = "This-is-a-Temp-File" + System.currentTimeMillis();
//            File tempFile = File.createTempFile(fileName, ".txt");
//            tempFile.deleteOnExit();
//            BufferedWriter tempFileWriter = new BufferedWriter(new FileWriter(tempFile));
//            tempFileWriter.write("this is a test file for upload" + +System.currentTimeMillis());
//            tempFileWriter.close();
            // upload a file to the created folder.
//            Map fileMap = new HashMap();
//            fileMap.put(tempFile.getName(), tempFile);
//            UploadRequest uploadRequest = BoxRequestFactory.createUploadRequest(accessToken, true, fileName, createdFolderId, fileMap);
//            UploadResponse uploadResponse = boxExternalAPI.upload(uploadRequest);
//            UploadResult uploadResult = (UploadResult) uploadResponse.getUploadResultList().get(0);
//            String uploadedFileId = uploadResult.getFile().getFileId();
//            LOGGER.debug("uploadedFileId is " + uploadedFileId);
//            HashMap nameBytesHashMap = new HashMap();
//            nameBytesHashMap.put("fileName.txt", "fileName.txt".getBytes());
//            uploadRequest = BoxRequestFactory.createUploadRequest(accessToken, false, createdFolderId, nameBytesHashMap);
//            boxExternalAPI.upload(uploadRequest);
//            LOGGER.debug("uploaded file");
            // share this folder
//            PublicShareRequest publicShareRequest = BoxRequestFactory.createPublicShareRequest(accessToken, "folder", "888888", createdFolderId, "this is my public folder !", null);
//            boxExternalAPI.publicShare(publicShareRequest);
//            LOGGER.debug("created public share");
            // get account file/folder tree structure
//            String[] params = { "nozip" };
//            GetAccountTreeRequest getAccountTreeRequest = BoxRequestFactory.createGetAccountTreeRequest(accessToken, "0", params);
//            boxExternalAPI.getAccountTree(getAccountTreeRequest);
//            LOGGER.debug("got account tree");
            // download the file
//            File tmpFile2 = new File("downloadedFileNo." + System.currentTimeMillis() + ".txt");
//            tmpFile2.createNewFile();
//            DownloadRequest downloadRequest = BoxRequestFactory.createDownloadRequest(accessToken, uploadedFileId, true, tmpFile2);
//            boxExternalAPI.download(downloadRequest);
//            LOGGER.debug("downloaded file");
            // delete this file
//            DeleteRequest deleteRequest = BoxRequestFactory.createDeleteRequest(accessToken, "file", uploadedFileId);
//            boxExternalAPI.delete(deleteRequest);
//            LOGGER.debug("deleted file");
            // User methods
            CreateUserRequest createUserRequest = BoxRequestFactory.createCreateUserRequest(accessToken, System.currentTimeMillis() + "@gmail.com", "Martin Gilchrist");
            CreateUserResponse createUserResponse = boxExternalAPI.createUser(createUserRequest);
            String userId = createUserResponse.getId();
            LOGGER.debug("userId is " + userId);
            GetUserInfoRequest getUserInfoRequest = BoxRequestFactory.createGetUserInfoRequest(accessToken, userId);
            GetUserInfoResponse getUserInfoResponse = boxExternalAPI.getUserInfo(getUserInfoRequest);
            String userName = getUserInfoResponse.getUser().getName();
            LOGGER.debug("userName is " + userName);
            UpdateUserRequest updateUserRequest = BoxRequestFactory.createUpdateUserRequest(accessToken, userId, "Martin P Gilchrist");
            UpdateUserResponse updateUserResponse = boxExternalAPI.updateUser(updateUserRequest);
            userName = updateUserResponse.getUser().getName();
            LOGGER.debug("userName is " + userName);

            // Group methods
            CreateGroupRequest createGroupRequest = BoxRequestFactory.createCreateGroupRequest(accessToken, "QA" + System.currentTimeMillis());
            CreateGroupResponse createGroupResponse = boxExternalAPI.createGroup(createGroupRequest);
            LOGGER.debug("created group");
            String groupId = createGroupResponse.getId();
            LOGGER.debug("groupId is " + groupId);
            GetGroupRequest getGroupRequest = BoxRequestFactory.createGetGroupRequest(accessToken, groupId);
            GetGroupResponse getGroupResponse = boxExternalAPI.getGroup(getGroupRequest);
            LOGGER.debug("got group");
            String groupName = getGroupResponse.getGroup().getName();
            LOGGER.debug("groupName is " + groupName);
            UpdateGroupRequest updateGroupRequest = BoxRequestFactory.createUpdateGroupRequest(accessToken, groupId, "Updated QA" + System.currentTimeMillis());
            UpdateGroupResponse updateGroupResponse = boxExternalAPI.updateGroup(updateGroupRequest);
            LOGGER.debug("updated group");
            groupName = updateGroupResponse.getGroup().getName();
            LOGGER.debug("groupName is " + groupName);

            // Collaboration methods
            CreateCollaborationRequest createCollaborationRequest = BoxRequestFactory.createCreateCollaborationRequest(accessToken, createdFolderId, userId, "viewer");
            CreateCollaborationResponse createCollaborationResponse = boxExternalAPI.createCollaboration(createCollaborationRequest);
            String collaborationId = createCollaborationResponse.getId();
            LOGGER.debug("collaborationId is " + collaborationId);
            GetCollaborationRequest getCollaborationRequest = BoxRequestFactory.createGetCollaborationRequest(accessToken, collaborationId);
            GetCollaborationResponse getCollaborationResponse = boxExternalAPI.getCollaboration(getCollaborationRequest);
            String collaborationLogin = getCollaborationResponse.getCollaboration().getLogin();
            LOGGER.debug("collaborationLogin is " + collaborationLogin);
            UpdateCollaborationRequest updateCollaborationRequest = BoxRequestFactory.createUpdateCollaborationRequest(accessToken, collaborationId, "editor");
            UpdateCollaborationResponse updateCollaborationResponse = boxExternalAPI.updateCollaboration(updateCollaborationRequest);
            collaborationId = updateCollaborationResponse.getId();
            LOGGER.debug("collaborationId is " + collaborationId);

            // Membership methods
            CreateMembershipRequest createMembershipRequest = BoxRequestFactory.createCreateMembershipRequest(accessToken, groupId, userId, "member");
            CreateMembershipResponse createMembershipResponse = boxExternalAPI.createMembership(createMembershipRequest);
            String membershipId = createMembershipResponse.getId();
            LOGGER.debug("membershipId is " + membershipId);
            GetMembershipRequest getMembershipRequest = BoxRequestFactory.createGetMembershipRequest(accessToken, membershipId);
            GetMembershipResponse getMembershipResponse = boxExternalAPI.getMembership(getMembershipRequest);
            membershipId = getMembershipResponse.getMembership().getId();
            LOGGER.debug("membershipId is " + membershipId);
            UpdateMembershipRequest updateMembershipRequest = BoxRequestFactory.createUpdateMembershipRequest(accessToken, membershipId, "admin");
            UpdateMembershipResponse updateMembershipResponse = boxExternalAPI.updateMembership(updateMembershipRequest);
            membershipId = updateMembershipResponse.getMembership().getId();
            LOGGER.debug("membershipId is " + membershipId);
            GetGroupsForUserRequest getGroupsForUserRequest = BoxRequestFactory.createGetGroupsForUserRequest(accessToken, userId);
            GetGroupsForUserResponse getGroupsForUserResponse = boxExternalAPI.getGroupsForUser(getGroupsForUserRequest);
            LOGGER.debug("got groups for user");
            List<BoxGroup> boxGroupUserList = getGroupsForUserResponse.getGroups();
            for (BoxGroup boxGroup : boxGroupUserList) {
                LOGGER.debug("groupId is " + boxGroup.getId());
            }

            GetMembershipsForGroupRequest getMembershipsForGroupRequest = BoxRequestFactory.createGetMembershipsForGroupRequest(accessToken, groupId);
            GetMembershipsForGroupResponse getMembershipsForGroupResponse = boxExternalAPI.getMembershipsForGroup(getMembershipsForGroupRequest);
            List<BoxMembership> boxMembershipGroupList = getMembershipsForGroupResponse.getMemberships();
            for (BoxMembership boxMembership : boxMembershipGroupList) {
                LOGGER.debug("membershipId is " + boxMembership.getId());
            }

            GetMembershipsForUserRequest getMembershipsForUserRequest = BoxRequestFactory.createGetMembershipsForUserRequest(accessToken, userId);
            GetMembershipsForUserResponse getMembershipsForUserResponse = boxExternalAPI.getMembershipsForUser(getMembershipsForUserRequest);
            List<BoxMembership> boxMembershipUserList = getMembershipsForUserResponse.getMemberships();
            for (BoxMembership boxMembership : boxMembershipUserList) {
                LOGGER.debug("membershipId is " + boxMembership.getId());
            }

            //Delete objects
            DeleteCollaborationRequest deleteCollaborationRequest = BoxRequestFactory.createDeleteCollaborationRequest(accessToken, collaborationId);
            DeleteCollaborationResponse deleteCollaborationResponse = boxExternalAPI.deleteCollaboration(deleteCollaborationRequest);
            LOGGER.debug("deleted collaboration");
            DeleteMembershipRequest deleteMembershipRequest = BoxRequestFactory.createDeleteMembershipRequest(accessToken, membershipId);
            DeleteMembershipResponse deleteMembershipResponse = boxExternalAPI.deleteMembership(deleteMembershipRequest);
            LOGGER.debug("deleted membership");
            DeleteGroupRequest deleteGroupRequest = BoxRequestFactory.createDeleteGroupRequest(accessToken, groupId);
            DeleteGroupResponse deleteGroupResponse = boxExternalAPI.deleteGroup(deleteGroupRequest);
            LOGGER.debug("deleted group");
            DeleteUserRequest deleteUserRequest = BoxRequestFactory.createDeleteUserRequest(accessToken, userId);
            DeleteUserResponse deleteUserResponse = boxExternalAPI.deleteUser(deleteUserRequest);
            LOGGER.debug("deleted user");

            // logout
            LogoutRequest logoutRequest = BoxRequestFactory.createLogoutRequest(accessToken, clientID, clientSecret);
            boxExternalAPI.logout(logoutRequest);
        } catch (IOException e) {
        } catch (BoxException e) {
        }
    }
}

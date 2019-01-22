/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.GetContactFolderRequest;

/**
 *
 * @author martin
 */
public class GetContactFolderRequestImpl extends MSGraphRequestImpl implements GetContactFolderRequest {
    private String contactFolderId;
    private String userId;

    public String getContactFolderId() {
        return this.contactFolderId;
    }
    
    public String getUserId() {
        return this.userId = userId;
    }

    public void setContactFolderId(String contactFolderId) {
        this.contactFolderId = contactFolderId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }    
}

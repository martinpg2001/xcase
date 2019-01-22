/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.GetMailFolderRequest;


/**
 *
 * @author martin
 */
public class GetMailFolderRequestImpl extends MSGraphRequestImpl implements GetMailFolderRequest {
    private String mailFolderId;
    private String userId;

    public String getMailFolderId() {
        return this.mailFolderId;
    }
    
    public String getUserId() {
        return this.userId = userId;
    }

    public void setMailFolderId(String mailFolderId) {
        this.mailFolderId = mailFolderId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
     
}

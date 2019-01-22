/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphMailFolder;
import com.xcase.msgraph.transputs.GetMailFoldersResponse;

/**
 *
 * @author martin
 */
public class GetMailFoldersResponseImpl extends MSGraphResponseImpl implements GetMailFoldersResponse {
    private MSGraphMailFolder[] mailFolders;
    
    public MSGraphMailFolder[] getMailFolders() {
        return this.mailFolders;
    }
    
    public void setMailFolders(MSGraphMailFolder[] mailFolders) {
        this.mailFolders = mailFolders;
    }    
}

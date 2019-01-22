/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphContactFolder;
import com.xcase.msgraph.transputs.GetContactFoldersResponse;

/**
 *
 * @author martin
 */
public class GetContactFoldersResponseImpl extends MSGraphResponseImpl implements GetContactFoldersResponse {
    private MSGraphContactFolder[] contactolders;
    
    public MSGraphContactFolder[] getContactFolders() {
        return this.contactolders;
    }
    
    public void setContactFolders(MSGraphContactFolder[] contactolders) {
        this.contactolders = contactolders;
    }        
}

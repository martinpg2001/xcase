/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphContactFolder;
import com.xcase.msgraph.transputs.GetContactFolderResponse;

/**
 *
 * @author martin
 */
public class GetContactFolderResponseImpl extends MSGraphResponseImpl implements GetContactFolderResponse {
    private MSGraphContactFolder msGraphContactFolder;
    
    public MSGraphContactFolder getContactFolder() {
        return this.msGraphContactFolder;
    }
    
    public void setContactFolder(MSGraphContactFolder msGraphContactFolder) {
        this.msGraphContactFolder = msGraphContactFolder;
    }     
}

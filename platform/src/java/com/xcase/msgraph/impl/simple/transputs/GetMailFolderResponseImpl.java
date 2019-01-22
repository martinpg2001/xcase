/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphMailFolder;
import com.xcase.msgraph.transputs.GetMailFolderResponse;

/**
 *
 * @author martin
 */
public class GetMailFolderResponseImpl extends MSGraphResponseImpl implements GetMailFolderResponse {
    private MSGraphMailFolder msGraphMailFolder;
    
    public MSGraphMailFolder getMailFolder() {
        return this.msGraphMailFolder;
    }
    
    public void setMailFolder(MSGraphMailFolder mailFolder) {
        this.msGraphMailFolder = mailFolder;
    }    
}

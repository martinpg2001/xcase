/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphDrive;
import com.xcase.msgraph.transputs.GetDriveResponse;

/**
 *
 * @author martin
 */
public class GetDriveResponseImpl extends MSGraphResponseImpl implements GetDriveResponse {
    private MSGraphDrive msGraphDrive;
    
    public MSGraphDrive getDrive() {
        return this.msGraphDrive;
    }
    
    public void setDrive(MSGraphDrive msGraphDrive) {
        this.msGraphDrive = msGraphDrive;
    }    
}

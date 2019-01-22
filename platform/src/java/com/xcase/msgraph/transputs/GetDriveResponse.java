/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.transputs;

import com.xcase.msgraph.objects.MSGraphDrive;

/**
 *
 * @author martin
 */
public interface GetDriveResponse extends MSGraphResponse {
    MSGraphDrive getDrive();
    void setDrive(MSGraphDrive msGraphDrive);
}

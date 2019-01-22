/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.transputs;

import com.xcase.msgraph.objects.MSGraphGroup;

/**
 *
 * @author martin
 */
public interface UpdateGroupRequest extends MSGraphRequest {
    MSGraphGroup getGroup();
    String getGroupId();
    void setGroup(MSGraphGroup group);
    void setGroupId(String groupId);      
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphGroup;
import com.xcase.msgraph.transputs.UpdateGroupRequest;


/**
 *
 * @author martin
 */
public class UpdateGroupRequestImpl extends MSGraphRequestImpl implements UpdateGroupRequest {
    private MSGraphGroup group;
    private String groupId;
    
    public MSGraphGroup getGroup() {
        return this.group;
    }
    
    public String getGroupId() {
        return this.groupId;
    }
    
    public void setGroup(MSGraphGroup group) {
        this.group = group;
    }
    
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
         
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.transputs;

/**
 *
 * @author martinpg
 */
public interface AddGroupMemberRequest extends MSGraphRequest {
    String getGroupId();
    String getMemberId();
    void setGroupId(String groupId);
    void setMemberId(String memberId);    
}

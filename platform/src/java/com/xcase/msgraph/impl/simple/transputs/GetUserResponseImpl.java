/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphUser;
import com.xcase.msgraph.transputs.GetUserResponse;

/**
 *
 * @author martin
 */
public class GetUserResponseImpl extends MSGraphResponseImpl implements GetUserResponse {
    private MSGraphUser user;
    
    public MSGraphUser getUser() {
        return this.user;
    }
    
    public void setUser(MSGraphUser user) {
        this.user = user;
    }    
}

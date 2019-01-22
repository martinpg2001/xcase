/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphProfile;
import com.xcase.msgraph.transputs.GetMyProfileResponse;

/**
 *
 * @author martin
 */
public class GetMyProfileResponseImpl extends MSGraphResponseImpl implements GetMyProfileResponse {
    private MSGraphProfile profile;
    
    public MSGraphProfile getProfile() {
        return this.profile;
    }
    
    public void setProfile(MSGraphProfile profile) {
        this.profile = profile;
    }    
}

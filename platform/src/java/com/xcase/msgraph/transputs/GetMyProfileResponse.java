/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.transputs;

import com.xcase.msgraph.objects.MSGraphProfile;

/**
 *
 * @author martin
 */
public interface GetMyProfileResponse extends MSGraphResponse {
    MSGraphProfile getProfile();
    void setProfile(MSGraphProfile profile);
}

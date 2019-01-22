/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.GetCalendarsRequest;
import com.xcase.msgraph.transputs.MSGraphQueryRequest;


/**
 *
 * @author martin
 */
public class GetCalendarsRequestImpl extends MSGraphQueryRequestImpl implements GetCalendarsRequest {
    private String userId;

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }    
}

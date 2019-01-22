/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.GetCalendarRequest;

/**
 *
 * @author martin
 */
public class GetCalendarRequestImpl extends MSGraphRequestImpl implements GetCalendarRequest {
    private String calendarId;
    private String userId;
    
    public String getCalendarId() {
        return this.calendarId;
    }
    
    public String getUserId() {
        return this.userId;
    }
    
    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }     
}

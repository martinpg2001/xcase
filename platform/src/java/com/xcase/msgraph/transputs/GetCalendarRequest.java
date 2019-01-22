/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.transputs;

/**
 *
 * @author martin
 */
public interface GetCalendarRequest extends MSGraphRequest {
    String getCalendarId();
    String getUserId();
    void setCalendarId(String calendarId);
    void setUserId(String userId);  
}

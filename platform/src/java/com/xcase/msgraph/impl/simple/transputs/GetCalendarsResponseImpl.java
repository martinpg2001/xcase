/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphCalendar;
import com.xcase.msgraph.transputs.GetCalendarsResponse;

/**
 *
 * @author martin
 */
public class GetCalendarsResponseImpl extends MSGraphResponseImpl implements GetCalendarsResponse {
    private MSGraphCalendar[] calendars;
    
    public MSGraphCalendar[] getCalendars() {
        return this.calendars;
    }
    
    public void setCalendars(MSGraphCalendar[] calendars) {
        this.calendars = calendars;
    }     
}

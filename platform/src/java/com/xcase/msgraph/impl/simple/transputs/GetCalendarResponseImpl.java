/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.objects.MSGraphCalendar;
import com.xcase.msgraph.transputs.GetCalendarResponse;

/**
 *
 * @author martin
 */
public class GetCalendarResponseImpl extends MSGraphResponseImpl implements GetCalendarResponse {
    private MSGraphCalendar msGraphCalendar;
    
    public MSGraphCalendar getCalendar() {
        return this.msGraphCalendar;
    }
    
    public void setCalendar(MSGraphCalendar calendar) {
        this.msGraphCalendar = calendar;
    }    
}

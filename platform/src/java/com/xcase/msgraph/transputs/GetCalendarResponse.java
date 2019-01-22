/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.transputs;

import com.xcase.msgraph.objects.MSGraphCalendar;

/**
 *
 * @author martin
 */
public interface GetCalendarResponse extends MSGraphResponse {
    MSGraphCalendar getCalendar();
    void setCalendar(MSGraphCalendar calendar);    
}

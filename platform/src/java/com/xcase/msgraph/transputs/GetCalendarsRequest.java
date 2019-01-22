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
public interface GetCalendarsRequest extends MSGraphQueryRequest {
    String getUserId();
    void setUserId(String userId);    
}

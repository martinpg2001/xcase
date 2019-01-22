/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.common.transputs;

import org.apache.http.StatusLine;

/**
 *
 * @author martinpg
 */
public interface CommonResponse {
    public int getResponseCode();

    public void setResponseCode(int responseCode);
    
    public StatusLine getStatusLine();
    
    public void setStatusLine(StatusLine statusLine);
}

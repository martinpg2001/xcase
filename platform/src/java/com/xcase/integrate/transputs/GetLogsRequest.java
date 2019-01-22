/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

import java.util.Date;

/**
 *
 * @author martin
 */
public interface GetLogsRequest extends IntegrateRequest {
    public Date getAfterDate();
    
    public Date getEndingAtDate();

    public Integer getEntriesPerPage();

    public Integer getEventId();
    
    public String getEventType();
    
    public String getLogType();
    
    public String getMessageType();
    
    public String getNextPageStartsAt();
    
    public String getRequestType();
    
    public String getResponseCode();
    
    public Integer getRuleId();
    
    public String getSecurityEventType();
    
    public String getSourceIp();
    
    public String getStartingId();

    public String getStatusString();
    
    public String getSystemComponent();
    
    public String getUsername();
    
    public void setAfterDate(Date afterDate);

    public void setEndingAtDate(Date endingAtDate);

    public void setEntriesPerPage(Integer entriesPerPage);
    
    public void setEventId(Integer eventId);
    
    public void setEventType(String eventType);
    
    public void setLogType(String logType);
    
    public void setMessageType(String messageType);
    
    public void setNextPageStartsAt(String nextPageStartsAt);
    
    public void setRequestType(String requestType);
    
    public void setResponseCode(String responseCode);
    
    public void setRuleId(Integer ruleId);
    
    public void setSecurityEventType(String securityEventType);
    
    public void setSourceIp(String sourceIp);
    
    public void setStartingId(String startingId);

    public void setStatusString(String status);
    
    public void setSystemComponent(String systemComponent);
    
    public void setUsername(String username);
}

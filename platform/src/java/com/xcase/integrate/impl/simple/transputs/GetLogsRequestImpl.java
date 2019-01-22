/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.GetLogsRequest;
import java.util.Date;

/**
 *
 * @author martin
 */
public class GetLogsRequestImpl extends IntegrateRequestImpl implements GetLogsRequest {

    private Date afterDate;

    private Date endingAtDate;

    private Integer entriesPerPage = Integer.valueOf(50);
    
    private Integer eventId;
    
    private String eventType;
    
    private String logType;
    
    private String messageType;

    private String nextPageStartsAt;
    
    private String requestType;
    
    private String responseCode;
    
    private Integer ruleId;
    
    private String securityEventType;
    
    private String sourceIp;
    
    private String startingId;

    private String status;
    
    private String systemComponent;
    
    private String username;

    public Date getAfterDate() {
        return this.afterDate;
    }

    public Date getEndingAtDate() {
        return this.endingAtDate;
    }

    public Integer getEntriesPerPage() {
        return this.entriesPerPage;
    }
    
    public Integer getEventId() {
        return this.eventId;
    }
    
    public String getEventType() {
        return this.eventType;
    }
    
    public String getLogType() {
        return this.logType;
    }
    
    public String getMessageType() {
        return this.messageType;
    }
    
    public String getNextPageStartsAt() {
        return this.nextPageStartsAt;
    }
    
    public String getRequestType() {
        return this.requestType;
    }
    
    public String getResponseCode() {
        return this.responseCode;
    }
    
    public Integer getRuleId() {
        return this.ruleId;
    }
    
    public String getSecurityEventType() {
        return this.securityEventType;
    }
    
    public String getSourceIp() {
        return this.sourceIp;
    }
    
    public String getStartingId() {
        return this.startingId;
    }

    public String getStatusString() {
        return this.status;
    }
    
    public String getSystemComponent() {
        return this.systemComponent;
    }
    
    public String getUsername() {
        return this.username;
    }

    public void setAfterDate(Date afterDate) {
        this.afterDate = afterDate;
    }

    public void setEndingAtDate(Date endingAtDate) {
        this.endingAtDate = endingAtDate;
    }

    public void setEntriesPerPage(Integer entriesPerPage) {
        this.entriesPerPage = entriesPerPage;
    }
    
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public void setLogType(String logType) {
        this.logType = logType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    
    public void setNextPageStartsAt(String nextPageStartsAt) {
        this.nextPageStartsAt = nextPageStartsAt;
    }
    
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
    
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }
    
    public void setSecurityEventType(String securityEventType) {
        this.securityEventType = securityEventType;
    }
    
    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }
    
    public void setStartingId(String startingId) {
        this.startingId = startingId;
    }

    public void setStatusString(String status) {
        this.status = status;
    }
    
    public void setSystemComponent(String systemComponent) {
        this.systemComponent = systemComponent;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}

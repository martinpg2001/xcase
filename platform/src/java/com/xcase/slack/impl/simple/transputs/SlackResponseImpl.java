package com.xcase.slack.impl.simple.transputs;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.transputs.CommonResponseImpl;
import com.xcase.slack.transputs.SlackResponse;

import org.apache.http.StatusLine;

public class SlackResponseImpl extends CommonResponseImpl implements SlackResponse {

    @Override
    public String getEntityString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusLine getStatusLine() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setEntityString(String entityString) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setStatus(String reasonPhrase) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setStatusLine(StatusLine statusLine) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getEventId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getEventType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonObject getEventMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getResponseCode() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setResponseCode(int responseCode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setEventId(String asString) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setEventType(String asString) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setEventMessage(JsonObject eventMessageJsonObject) {
        // TODO Auto-generated method stub

    }

}

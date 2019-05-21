package com.xcase.common.impl.simple.core;

public class MultiPartContent {
    private String fileName;
    private boolean isText;
    private byte[] content;
    private String contentType;
    
    public MultiPartContent(boolean isText, byte[] content, String contentType, String fileName) {
        isText(isText);
        setContent(content);
        setContentType(contentType);
        setFileName(fileName);
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public boolean isText() {
        return isText;
    }
    
    public byte[] getContent() {
        return content;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void isText(boolean isText) {
        this.isText = isText;
    }
    
    public void setContent(byte[] content) {
        this.content = content;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}

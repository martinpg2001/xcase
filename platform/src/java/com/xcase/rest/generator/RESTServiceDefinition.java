package com.xcase.rest.generator;

import com.xcase.rest.generator.IServiceDefinition;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RESTServiceDefinition implements IServiceDefinition
{
    public List<ClassDefinition> Classes;
    public String EndPoint;
    public String ObjectsPackageName;
    public String[] SourceStrings;
    public HashMap<String, ProxyDefinition> ProxyClasses = new HashMap<String, ProxyDefinition>();
    
    public RESTServiceDefinition() {
        
    }
    
    public RESTServiceDefinition(String objectsPackageName) {
        this.ObjectsPackageName = objectsPackageName;
    }    

    public String getEndPoint() {
        return EndPoint;
    }

    public String[] getSourceStrings() {
        return SourceStrings;
    }

    public HashMap<String, ProxyDefinition> getProxyClasses() {
        return ProxyClasses;
    }
    
    public void writeToStringBuilders() {
        for (String proxy : ProxyClasses.keySet()) {
            ProxyDefinition proxyDefinition = ProxyClasses.get(proxy);
            proxyDefinition.writeToStringBuilder(proxy, ObjectsPackageName, "");
        }
        
        for (ClassDefinition classDefinition : Classes) {
            classDefinition.writeToStringBuilder(ObjectsPackageName);
        }        
    }
}

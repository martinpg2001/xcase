package com.xcase.rest.generator.raml;

import com.google.gson.*;
import com.xcase.rest.generator.*;
import com.xcase.rest.generator.raml.*;
import com.xcase.rest.generator.IProxyGenerator;
import com.xcase.rest.generator.RESTApiProxySettings;
import com.xcase.rest.generator.RESTApiProxySettingsEndpoint;
import com.xcase.rest.generator.RESTServiceDefinition;
import java.lang.invoke.MethodHandles;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RAMLJavaProxyGenerator extends RESTProxyGenerator implements IProxyGenerator {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    private static String objectsPackageName;
    private static String objectsPath;
    private static String packageName;
    
    public RAMLJavaProxyGenerator(String packageName) {
        this.packageName = packageName;
        this.objectsPackageName = this.packageName + ".objects";
        this.objectsPath = this.objectsPackageName.replace(".", "/");
    }

    @Override
    public RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint swaggerApiProxySettingsEndPoint, String swaggerDocument) throws Exception {
        LOGGER.debug("starting generateSourceString()");
        return null; 
    }

    @Override
    public RESTServiceDefinition generateSourceString(String swaggerDocument) throws Exception {
        LOGGER.debug("starting GenerateSourceString()");
        try {
            endpointHashMap = new HashMap<IAPIProxySettingsEndpoint, String>();
            RESTApiProxySettingsEndpoint swaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndpoint();
            swaggerApiProxySettingsEndPoint.AppendAsyncToMethodName = false;
            swaggerApiProxySettingsEndPoint.Namespace = "com.xcase.integrate.objects";
            endpointHashMap.put(swaggerApiProxySettingsEndPoint, swaggerDocument);
            LOGGER.debug("about to process RAML document");
            return processRAMLDocuments();
        }
        catch (Exception e) {
            LOGGER.warn("exception generating source string: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint swaggerApiProxySettingsEndPoint, String swaggerDocument, String username, String password, String tenant) throws Exception {
        LOGGER.debug("starting generateSourceString()");
        try {
            endpointHashMap = new HashMap<IAPIProxySettingsEndpoint, String>();
            //SwaggerApiProxySettingsEndPoint swaggerApiProxySettingsEndPoint = new SwaggerApiProxySettingsEndPoint();
            ((RESTApiProxySettingsEndpoint)swaggerApiProxySettingsEndPoint).AppendAsyncToMethodName = false;
            endpointHashMap.put(swaggerApiProxySettingsEndPoint, swaggerDocument);
            LOGGER.debug("about to process RAML documents");
            RESTServiceDefinition restServiceDefinition = processRAMLDocuments(username, password, tenant);
            LOGGER.debug("processed RAML documents");
            return restServiceDefinition;
        }
        catch (Exception e) {
            LOGGER.warn("exception generating source string: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint[] endpoints) throws Exception {
        LOGGER.debug("starting generateSourceString()");
        try {
            endpointHashMap = new HashMap<IAPIProxySettingsEndpoint, String>();
            LOGGER.debug("requesting RAML documents...");
            for (IAPIProxySettingsEndpoint endPoint : endpoints) {
                String requestUri = endPoint.getUrl();
                LOGGER.debug("requested: " + requestUri);
                getEndpointDoc(requestUri, endPoint);
            }

            LOGGER.debug("waiting for REST documents to complete downloading...");
            return processRAMLDocuments();
        }
        catch (Exception e) {
            LOGGER.warn("exception generating source string: " + e.getMessage());
            throw e;
        }
    }
    
    public List<RESTServiceDefinition> generateSourceStringForRestServiceDefinitionList(IAPIProxySettingsEndpoint swaggerApiProxySettingsEndPoint, String swaggerDocument, String username, String password, String tenant) throws Exception {
//        LOGGER.debug("starting generateSourceStringForRestServiceDefinitionList()");
        List<RESTServiceDefinition> restServiceDefinitionList = new ArrayList<RESTServiceDefinition>();
        
        return restServiceDefinitionList;
    }

    private static RESTServiceDefinition processRAMLDocuments(String username, String password, String tenant) throws Exception {
//        LOGGER.debug("starting processRAMLDocuments()");
        RESTServiceDefinition restServiceDefinition = new RESTServiceDefinition();
        List<String> sourceStringList = new ArrayList<String>();
        for (Map.Entry<IAPIProxySettingsEndpoint, String> docDictionaryEntry : endpointHashMap.entrySet()) {
            IAPIProxySettingsEndpoint endPoint = docDictionaryEntry.getKey();
            LOGGER.debug("processing " + endPoint.getUrl());
            String methodNameAppend = "";
            if (endPoint.getAppendAsyncToMethodName()) {
                methodNameAppend = "Async";
            }

            String result = docDictionaryEntry.getValue();
            RAMLParser parser = new RAMLParser();
            ProxyDefinition proxyDefinition = parser.parseDoc(result, (RESTApiProxySettingsEndpoint)endPoint);
            String endPointString = "http://" + proxyDefinition.Host + proxyDefinition.BasePath;
            if (!endPointString.endsWith("/")) {
                endPointString = endPointString + "/";
            }

            restServiceDefinition.EndPoint = endPointString;
            List<Operation> operationList = proxyDefinition.Operations;
            List<String> proxies = new ArrayList<String>();
            for (Operation operation : operationList) {
                if (!proxies.contains(operation.ProxyName)) {
                    proxies.add(operation.ProxyName);
                }
            }
            
            /* Write interface for each proxy in proxies */
            for (String proxy : proxies) {
//                LOGGER.debug("next proxy " + proxy);
                StringBuilder interfaceStringBuilder = writeInterfaceStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend);
//                LOGGER.debug("created interfaceStringBuilder for " + proxy);
                sourceStringList.add(interfaceStringBuilder.toString());
            }

            /* Write class for each proxy in proxies */
            for (String proxy : proxies) {
//                LOGGER.debug("next proxy " + proxy);
                String className = RESTParser.fixTypeName(proxy) + "WebProxy";
//                LOGGER.debug("proxy className is " + className);
                restServiceDefinition.ProxyClasses.put(className, proxyDefinition);
                StringBuilder classStringBuilder = writeProxyStringBuilderForProxy(proxyDefinition, proxy, endPoint, methodNameAppend);
//                LOGGER.debug("created classStringBuilder for " + proxy);
                sourceStringList.add(classStringBuilder.toString());
            }

            /* Model Classes */
            for (ClassDefinition classDefinition : proxyDefinition.ClassDefinitions) {
                StringBuilder modelClassStringBuilder = writeClassDefinitionToStringBuilder(classDefinition, endPoint);
                sourceStringList.add(modelClassStringBuilder.toString());
            }
        }

        restServiceDefinition.SourceStrings = sourceStringList.toArray(new String[0]);
        return restServiceDefinition;
    }

    private static RESTServiceDefinition processRAMLDocuments() throws Exception {
//        LOGGER.debug("starting processRAMLDocuments()");
        return processRAMLDocuments("Admin", "1nt@ppC10ud2016", "tenant1");
    }
}

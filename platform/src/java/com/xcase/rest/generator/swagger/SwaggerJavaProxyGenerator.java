package com.xcase.rest.generator.swagger;

import com.xcase.rest.generator.RESTServiceDefinition;
import com.xcase.rest.generator.RESTApiProxySettingsEndpoint;
import com.xcase.rest.generator.RESTApiProxySettings;
import com.xcase.rest.generator.IProxyGenerator;
import com.xcase.rest.generator.IAPIProxySettingsEndpoint;
import com.xcase.rest.generator.ClassDefinition;
import com.xcase.rest.generator.ProxyDefinition;
import com.xcase.rest.generator.TypeDefinition;
import com.xcase.rest.generator.Parameter;
import com.xcase.rest.generator.RESTParser;
import com.xcase.rest.generator.ParameterIn;
import com.xcase.rest.generator.Operation;
import com.google.gson.*;
import java.lang.invoke.MethodHandles;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwaggerJavaProxyGenerator extends SwaggerProxyGenerator implements IProxyGenerator
{
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public SwaggerJavaProxyGenerator(String packageName) {
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
            LOGGER.debug("about to process REST document");
            return processSwaggerDocuments();
        }
        catch (Exception e) {
            LOGGER.warn("exception generating source string: " + e.getMessage());
            throw e;
        }
    }
    
    public List<RESTServiceDefinition> generateSourceStringForRestServiceDefinitionList(IAPIProxySettingsEndpoint swaggerApiProxySettingsEndPoint, String swaggerDocument, String username, String password, String tenant) throws Exception {
        LOGGER.debug("starting generateSourceString()");
        try {
            endpointHashMap = new HashMap<IAPIProxySettingsEndpoint, String>();
            ((RESTApiProxySettingsEndpoint)swaggerApiProxySettingsEndPoint).AppendAsyncToMethodName = false;
            endpointHashMap.put(swaggerApiProxySettingsEndPoint, swaggerDocument);
            LOGGER.debug("about to process REST document");
            List<RESTServiceDefinition> restServiceDefinitionList = processSwaggerDocumentsForRestServicDefinitionList(objectsPackageName, username, password, tenant);
            LOGGER.debug("processed REST document");
            return restServiceDefinitionList;
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
            ((RESTApiProxySettingsEndpoint)swaggerApiProxySettingsEndPoint).AppendAsyncToMethodName = false;
            endpointHashMap.put(swaggerApiProxySettingsEndPoint, swaggerDocument);
            LOGGER.debug("about to process REST document");
            RESTServiceDefinition restServiceDefinition = processSwaggerDocuments(username, password, tenant);
            LOGGER.debug("processed REST document");
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
            LOGGER.debug("requesting REST documents...");
            for (IAPIProxySettingsEndpoint endPoint : endpoints) {
                String requestUri = endPoint.getUrl();
                LOGGER.debug("requested: " + requestUri);
                getEndpointDoc(requestUri, endPoint);
            }

            LOGGER.debug("waiting for REST documents to complete downloading...");
            return processSwaggerDocuments();
        }
        catch (Exception e) {
            LOGGER.warn("exception generating source string: " + e.getMessage());
            throw e;
        }
    }
    
    private static List<RESTServiceDefinition> processSwaggerDocumentsForRestServicDefinitionList(String objectsPackageName, String username, String password, String tenant) throws Exception {
        LOGGER.debug("starting processSwaggerDocuments()");
        List<RESTServiceDefinition> swaggerServiceDefinitionList = new ArrayList<RESTServiceDefinition>();
        for (Map.Entry<IAPIProxySettingsEndpoint, String> swaggerDocDictionaryEntry : endpointHashMap.entrySet()) {
            IAPIProxySettingsEndpoint endPoint = swaggerDocDictionaryEntry.getKey();
            LOGGER.debug("processing " + endPoint.getUrl());
            String methodNameAppend = "";
            if (endPoint.getAppendAsyncToMethodName()) {
                methodNameAppend = "Async";
            }

            String result = swaggerDocDictionaryEntry.getValue();
            SwaggerParser parser = new SwaggerParser();
            RESTServiceDefinition restServiceDefinition = parser.parseDocForRestServiceDefinition(result, (RESTApiProxySettingsEndpoint)endPoint, objectsPackageName);
            restServiceDefinition.writeToStringBuilders();
            swaggerServiceDefinitionList.add(restServiceDefinition);
        }
        
        LOGGER.debug("finishing processSwaggerDocuments()");
        return swaggerServiceDefinitionList;
    }

    private static RESTServiceDefinition processSwaggerDocuments(String username, String password, String tenant) throws Exception {
//        LOGGER.debug("starting processSwaggerDocuments()");
        RESTServiceDefinition swaggerServiceDefinition = new RESTServiceDefinition();
        List<String> sourceStringList = new ArrayList<String>();
        for (Map.Entry<IAPIProxySettingsEndpoint, String> swaggerDocDictionaryEntry : endpointHashMap.entrySet()) {
            IAPIProxySettingsEndpoint endPoint = swaggerDocDictionaryEntry.getKey();
            LOGGER.debug("processing " + endPoint.getUrl());
            String methodNameAppend = "";
            if (endPoint.getAppendAsyncToMethodName()) {
                methodNameAppend = "Async";
            }

            String result = swaggerDocDictionaryEntry.getValue();
            SwaggerParser parser = new SwaggerParser();
            ProxyDefinition proxyDefinition = parser.parseDoc(result, (RESTApiProxySettingsEndpoint)endPoint);
            String endPointString = "http://" + proxyDefinition.Host + proxyDefinition.BasePath;
            if (!endPointString.endsWith("/")) {
                endPointString = endPointString + "/";
            }

            swaggerServiceDefinition.EndPoint = endPointString;
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
                String className = SwaggerParser.fixTypeName(proxy) + "WebProxy";
//                LOGGER.debug("proxy className is " + className);
                swaggerServiceDefinition.ProxyClasses.put(className, proxyDefinition);
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

        swaggerServiceDefinition.SourceStrings = sourceStringList.toArray(new String[0]);
        return swaggerServiceDefinition;
    }

    private static RESTServiceDefinition processSwaggerDocuments() throws Exception {
//        LOGGER.debug("starting processSwaggerDocuments()");
        return processSwaggerDocuments("Admin", "1nt@ppC10ud2016", "tenant1");
    }

    public static RESTApiProxySettings getSettings(String path) throws Exception {
        String value = new String(Files.readAllBytes(Paths.get(path)));
        return new Gson().fromJson(value, RESTApiProxySettings.class);
    }
}


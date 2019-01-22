package com.xcase.rest.generator.raml;

import com.xcase.rest.generator.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import java.lang.invoke.MethodHandles;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.raml.v2.api.*;
//import org.raml.v2.api.model.common.ValidationResult;
//import org.raml.v2.api.model.v08.api.*;
//import org.raml.v2.api.model.v10.api.*;

public class RAMLParser extends RESTParser {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static boolean isNullOrEmpty(String s) {
        if (s == null) {
            return true;
        } else if (s.equals("")) {
            return true;
        } else {
            return false;
        }
    }
    
    public String firstToUpperCase(String s) {
        if (s == null) {
            return null;
        } else {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
    }
    
//    public ProxyDefinition parseDoc(String document, IAPIProxySettingsEndpoint endpoint) {
//        LOGGER.debug("starting parseDoc()");
//        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi("https://raw.githubusercontent.com/raml-org/raml-tutorial-200/step8/jukebox-api.raml");
//        if (ramlModelResult.hasErrors())
//        {
//            for (ValidationResult validationResult : ramlModelResult.getValidationResults())
//            {
//                System.out.println(validationResult.getMessage());
//            }
//        }
//        else
//        {
//            ProxyDefinition proxyDefinition = null;
//            if (ramlModelResult.isVersion08()) {
//                org.raml.v2.api.model.v08.api.Api api = ramlModelResult.getApiV08();
//                proxyDefinition = createProxyDefinitionFromV08Api(api);
//            } else if (ramlModelResult.isVersion10()) {
//                org.raml.v2.api.model.v10.api.Api api = ramlModelResult.getApiV10();
//                proxyDefinition = createProxyDefinitionFromV10Api(api);
//            } else {
//                LOGGER.warn("unrecognized RAML version");
//            }
//            
//            return proxyDefinition;
//        }
//        
//        return null;
//    }
    
//    public ProxyDefinition createProxyDefinitionFromV08Api(org.raml.v2.api.model.v08.api.Api api) {
//        ProxyDefinition proxyDefinition = new ProxyDefinition();
//        if (api != null) {
//            proxyDefinition.BasePath = api.baseUri().value();
//            proxyDefinition.Title = api.title();
//            proxyDefinition.Version = api.version();
//            proxyDefinition.Operations = createOperationsFromV08Api(api);
//        }
//        
//        return proxyDefinition;
//    }
//    
//    public ProxyDefinition createProxyDefinitionFromV10Api(org.raml.v2.api.model.v10.api.Api api) {
//        ProxyDefinition proxyDefinition = new ProxyDefinition();
//        if (api != null) {
//            proxyDefinition.BasePath = api.baseUri().value();
//            proxyDefinition.Title = api.title().value();
//            proxyDefinition.Version = api.version().value();
//            proxyDefinition.Operations = createOperationsFromV10Api(api);
//        }
//        
//        return proxyDefinition;
//    }
//    
//    public List<Operation> createOperationsFromV08Api(org.raml.v2.api.model.v08.api.Api api) {
//        List<Operation> operationList = new ArrayList<Operation>();
//        
//        return operationList;
//    }
//    
//    public List<Operation> createOperationsFromV10Api(org.raml.v2.api.model.v10.api.Api api) {
//        List<Operation> operationList = new ArrayList<Operation>();
//        
//        return operationList;
//    }
    
    public RESTServiceDefinition parseDocForRestServiceDefinition(String document, IAPIProxySettingsEndpoint endpoint, String objectsPackageName) throws Exception {
        LOGGER.debug("starting parseDocForRestServiceDefinition()");
        return new RESTServiceDefinition();
    }

    public ProxyDefinition parseDoc(String document, IAPIProxySettingsEndpoint endpoint) {
        LOGGER.debug("starting parseDoc()");
        Deserializer deserializer = new DeserializerBuilder().build();
        LOGGER.debug("document is " + document);
        HashMap<String, Object> ramlObject = (HashMap<String, Object>)deserializer.deserialize(document);
        LOGGER.debug("got ramlObject");
        ProxyDefinition proxyDefinition = new ProxyDefinition();
        if (ramlObject != null) {
            proxyDefinition.Title = ramlObject.containsKey("title") ? ramlObject.get("title").toString() : null;
            proxyDefinition.Version = ramlObject.containsKey("version") ? ramlObject.get("version").toString() : null;
            String baseUri = ramlObject.containsKey("baseUri") ? ramlObject.get("baseUri").toString() : null;
            LOGGER.debug("baseUri is " + baseUri);
            if (!isNullOrEmpty(baseUri)) {
                if (!baseUri.endsWith("/")) {
                    baseUri = baseUri + "/";
                }

                proxyDefinition.BasePath = baseUri + proxyDefinition.Version + "/";
            } else {
                proxyDefinition.BasePath = endpoint.getBasePath();
            }

            proxyDefinition.Schemes = ramlObject.keySet().contains("protocols") ? parseProtocolsForSchemes(ramlObject.get("protocols")) : new String[] { "http" };
            LOGGER.debug("proxyDefinition Description is " + proxyDefinition.Description);
            LOGGER.debug("proxyDefinition Title is " + proxyDefinition.Title);
            LOGGER.debug("proxyDefinition Version is " + proxyDefinition.Version);
            LOGGER.debug("proxyDefinition BasePath is " + proxyDefinition.BasePath);
            LOGGER.debug("proxyDefinition schemes is " + proxyDefinition.Schemes);
            this.parsePaths(ramlObject, proxyDefinition, endpoint.getParseOperationIdForProxyName());
            this.parseDefinitions(ramlObject, proxyDefinition);
        }

        LOGGER.debug("finishing parseDoc()");
        return proxyDefinition;
    }

    private String[] parseProtocolsForSchemes(Object p) {
        if (p != null) {
            return (String[])p;
        } else if (p != null) {
            return ((String)p).split(",");
        } else {
            return new String[] { "http" };
        }
    }

    private void parsePaths(HashMap<String, Object> ramlObject, ProxyDefinition proxyDefinition, boolean parseOperationIdForProxyName) {
        LOGGER.debug("starting parsePaths()");
        List<Operation> pathOperationList = createOperationListFromRAMLObject(ramlObject, parseOperationIdForProxyName);
        LOGGER.debug("created pathOperationList");
        for (Operation operation : pathOperationList) {
            proxyDefinition.Operations.add(operation);
        }
        
        LOGGER.debug("finishing parsePaths()");
    }

    private List<Operation> createOperationListFromRAMLObject(HashMap<String, Object> ramlObject, boolean parseOperationIdForProxyName) {
        LOGGER.debug("starting createOperationListFromRAMLObject()");
        List<Operation> operationList = new ArrayList<Operation>();
        for (Map.Entry<String, Object> pathKeyValuePair : ramlObject.entrySet()) {
            List<Operation> pathOperationList = createPathOperationListFromKeyValuePair(pathKeyValuePair, parseOperationIdForProxyName);
            for (Operation operation : pathOperationList) {
                operationList.add(operation);
            }
        }

        return operationList;
    }

    private List<Operation> createPathOperationListFromKeyValuePair(Map.Entry<String, Object> pathKeyValuePair, boolean parseOperationIdForProxyName) {
        LOGGER.debug("starting createOperationListFromKeyValuePair()");
        List<Operation> pathOperationList = new ArrayList<Operation>();
        String path = (String)pathKeyValuePair.getKey();
        LOGGER.debug("path is " + path);
        pathOperationList = createOperationListFromPathKeyValuePair(pathKeyValuePair, path, parseOperationIdForProxyName, new ArrayList<Parameter>());
        return pathOperationList;
    }

    private List<Operation> createOperationListFromPathKeyValuePair(Map.Entry<String, Object> pathKeyValuePair, String path, boolean parseOperationIdForProxyName, List<Parameter> parameterList) {
        LOGGER.debug("starting createOperationListFromPathKeyValuePair()");
        List<Operation> pathOperationList = new ArrayList<Operation>();
        List<Parameter> pathParameterList = parameterList;
        LOGGER.debug("path is " + path);
        String[] methodArray = new String[] { "get", "delete", "patch", "post", "put" };
        if (pathKeyValuePair.getValue() instanceof HashMap) {
            for (String subKey : ((HashMap<String, Object>)pathKeyValuePair.getValue()).keySet()) {
                LOGGER.debug("subKey is " + subKey);
                Object subPathObject = ((HashMap<String, Object>)pathKeyValuePair.getValue()).get(subKey);
                if (Arrays.asList(methodArray).contains(subKey)) {
                    LOGGER.debug("subKey is method");
                    String returnType = null;
                    if (((HashMap<String, Object>)pathKeyValuePair.getValue()).containsKey("type") && ((HashMap<String, Object>)((HashMap<String, Object>)pathKeyValuePair.getValue()).get("type") != null)) {
                        Object returnTypeObject = ((HashMap<String, Object>)((HashMap<String, Object>)pathKeyValuePair.getValue())).get("type");
                        if (((HashMap<String, Object>)((HashMap<String, Object>)returnTypeObject)).containsKey("collection") && ((HashMap<String, Object>)((HashMap<String, Object>)returnTypeObject)).get("collection") != null) {
                            returnType = "object[]";
                        } else {
                            returnType = "object";
                        }
                    }

                    Operation pathOperation = createOperationFromObject(subKey, subPathObject, returnType, path, parseOperationIdForProxyName, parameterList);
                    pathOperationList.add(pathOperation);
                }
                else if (subKey.startsWith("/")) {
                    if (subKey.contains("{")) {
                        LOGGER.debug("subKey is path parameter");
                        String extendedPath = path + subKey;
                        /* TODO: how to split subkey for parameter names */
                        String parameterName = subKey.split("{")[1];
                        LOGGER.debug("parameterName is " + parameterName);
                        Parameter subPathParameter = new Parameter(new TypeDefinition("String", parameterName, null, false), ParameterIn.Path, true, null, null);
                        pathParameterList.add(subPathParameter);
                        for (Map.Entry<String, Object> subPathKeyValuePair : ((HashMap<String, Object>)pathKeyValuePair.getValue()).entrySet()) {
                            List<Operation> subPathOperationList = createOperationListFromPathKeyValuePair(subPathKeyValuePair, extendedPath, parseOperationIdForProxyName, pathParameterList);
                            for (Operation operation : subPathOperationList) {
                                pathOperationList.add(operation);
                            }
                        }

                        pathParameterList.remove(subPathParameter);
                    } else {
                        LOGGER.debug("subKey is path");
                        String extendedPath = path + subKey;
                        for (Map.Entry<String, Object> subPathKeyValuePair : ((HashMap<String, Object>)pathKeyValuePair.getValue()).entrySet()) {
                            List<Operation> subPathOperationList = createOperationListFromPathKeyValuePair(subPathKeyValuePair, extendedPath, parseOperationIdForProxyName, parameterList);
                            for (Operation operation : subPathOperationList) {
                                pathOperationList.add(operation);
                            }
                        }
                    }
                }
            }
        }

        return pathOperationList;
    }

    private Operation createOperationFromObject(String methodString, Object methodObject, String returnType, String path, boolean parseOperationIdForProxyName, List<Parameter> parameterList) {
        LOGGER.debug("starting CreateOperationFromObject()");
        LOGGER.debug("methodString is " + methodString);
        List<Parameter> operationParameterList = parameterList;
        if (methodObject instanceof HashMap) {
            LOGGER.debug("methodObject is dictionary");
            String operationId = "";
            String proxyName = "";
            if (((HashMap<String, Object>)methodObject).containsKey("operationId") && ((HashMap<Object, Object>)methodObject).get("operationId") != null) {
                LOGGER.debug("operationId is not null");
                operationId = ((HashMap<Object, Object>)methodObject).get("operationId").toString();
                LOGGER.debug("operationId is " + operationId);
                if (parseOperationIdForProxyName) {
                    if (operationId.contains("_")) {
                        int underscoreLocation = operationId.indexOf("_");
                        proxyName = operationId.substring(0, underscoreLocation);
                        operationId = operationId.substring(underscoreLocation + 1);
                    }
                }

                LOGGER.debug("proxyName is " + proxyName);
                if (isNullOrEmpty(proxyName)) {
                    /* Did not get the proxy name from the operation id, so take the first tag value */
                    //object tagToken = ((Dictionary<String, object>)methodObject)["tags"];
                    //if (tagToken != null)
                    //{
                    //    List<String> tags = tagToken.ToObject<List<String>>();
                    //    String firstTag = tags.First();
                    //    proxyName = FixTypeName(firstTag);
                    //}
                }
            } else {
                LOGGER.debug("operationId is null");
                String[] pathArray = path.split("/");
                String methodPath = "";
                for (String pathElement : pathArray) {
                    if (!pathElement.contains("{")) {
                        methodPath = methodPath + firstToUpperCase(pathElement);
                    }
                }

                operationId = firstToUpperCase(methodString) + methodPath;
            }

            String description = "";
            if (((HashMap<String, Object>)methodObject).containsKey("description") && ((Dictionary<Object, Object>)methodObject).get("description") != null) {
                LOGGER.debug("descriptionObject is not null");
                description = ((HashMap<Object, Object>)methodObject).get("description").toString();
            }

            LOGGER.debug("description is " + description);
            LOGGER.debug("about to create operation");
            Operation operation = new Operation(returnType, methodString, path, operationParameterList, operationId, description, proxyName);
            LOGGER.debug("created operation");
            return operation;
        }
        else {
            LOGGER.debug("methodObject is not dictionary");
        }

        return null;
    }

    private Parameter createParameterFromJsonElement(JsonObject paramToken) {
        return new Parameter(new TypeDefinition("", "", null, false), ParameterIn.Body, false, null, null);
        /* TODO: process parameters */
        //TypeDefinition type = ParseType(paramToken);
        //bool isRequired = false;
        //if (paramToken["required"] != null)
        //{
        //    isRequired = paramToken["required"].ToObject<bool>();
        //}

        //ParameterIn parameterIn = ParameterIn.Body;
        //if (paramToken["in"] != null)
        //{
        //    if (paramToken["in"].ToString().Equals("path"))
        //    {
        //        parameterIn = ParameterIn.Path;
        //    }
        //    else if (paramToken["in"].ToString().Equals("query"))
        //    {
        //        parameterIn = ParameterIn.Query;
        //    }
        //    else if (paramToken["in"].ToString().Equals("formData"))
        //    {
        //        parameterIn = ParameterIn.FormData;
        //    }
        //}

        //JToken propDescriptionToken = paramToken["description"];
        //String propDescription = "";
        //if (propDescriptionToken != null)
        //{
        //    propDescription = propDescriptionToken.ToString();
        //}

        //String collectionFormat = "";
        //JToken collectionFormatToken = paramToken["collectionFormat"];
        //if (collectionFormatToken != null)
        //{
        //    collectionFormat = collectionFormatToken.ToString();
        //}

        //Parameter parameter = new Parameter(type, parameterIn, isRequired, propDescription, collectionFormat);
        //return parameter;
    }

    private void parseDefinitions(HashMap<String, Object> ramlObject, ProxyDefinition proxyDefinition) {
        LOGGER.debug("starting parseDefinitions()");
        if (((HashMap<String, Object>)ramlObject).containsKey("types")) {
            LOGGER.debug("ramlObject contains types");
            Object typesObject = ((HashMap<String, Object>)ramlObject).get("types");
            if (typesObject instanceof HashMap) {
                for (Map.Entry<Object, Object> classKeyValuePair : ((HashMap<Object, Object>)typesObject).entrySet()) {
                    LOGGER.debug("key is " + classKeyValuePair.getKey());
                    boolean addIt = true;
                    ClassDefinition classDefinition = new ClassDefinition((String)classKeyValuePair.getKey());
                    HashMap<String, Object> classDictionary = (HashMap<String, Object>) classKeyValuePair.getValue();
                    HashMap<String, Object> propertiesDictionary = (HashMap<String, Object>) classDictionary.get("properties");
                    if (propertiesDictionary != null && propertiesDictionary instanceof HashMap) {
                        for (Map.Entry<String, Object> propertyKeyValuePair : ((HashMap<String, Object>)propertiesDictionary).entrySet()) {
                            TypeDefinition type = parseType(propertyKeyValuePair);
                            classDefinition.Properties.add(type);
                        }
                    }
                    else {
                        addIt = false;
                    }

                    classDefinition.Name = fixGenericName(classDefinition.Name);
                    if (classDefinition.Name.equals("Void")) {
                        addIt = false;
                    }

                    if (addIt) {
                        proxyDefinition.ClassDefinitions.add(classDefinition);
                    }
                }
            }
        }
        else {
            LOGGER.debug("ramlObject does not contain types");
        }

        LOGGER.debug("finishing ParseDefinitions()");
    }

    private TypeDefinition parseType(Map.Entry<String, Object> token) {
        boolean isNullable = true;
        String name = "";
        if (token.getKey() instanceof String) {
            name = (String)token.getKey();
        }

        Dictionary<Object, Object> typeDictionary = (Dictionary<Object, Object>) token.getValue();
        String required = (String)typeDictionary.get("required");
        if (!isNullOrEmpty(required) && "true".equals(required)) {
            isNullable = false;
        }

        String typeName = (String)typeDictionary.get("type");
        Object enumToken = typeDictionary.get("enum");
        String[] enumValues = null;
        if (enumToken != null) {
            /* TODO: fix enum processing */
            //List<String> enumList = new List<String>();
            //bool anyRawNumbers = false;
            //foreach (JToken enumValueToken in enumToken)
            //{
            //    String enumValue = enumValueToken.ToString();
            //    decimal value;
            //    if (Decimal.TryParse(enumValue, out value))
            //    {
            //        anyRawNumbers = true;
            //    }

            //    enumList.Add(enumValue);
            //}

            //if (anyRawNumbers == false)
            //{
            //    enumValues = enumList.ToArray();
            //    typeName = FixTypeName(name + "Values");
            //}
        }

        typeName = fixGenericName(typeName);
        TypeDefinition type = new TypeDefinition(typeName, name, enumValues, isNullable);
        return type;
    }

    private String parseRef(String input) {
        return input.startsWith("#/definitions/") ? input.substring("#/definitions/".length()) : input;
    }
}

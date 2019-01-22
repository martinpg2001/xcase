package com.xcase.rest.generator.swagger;

import com.xcase.rest.generator.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.rest.generator.RESTApiProxySettingsEndpoint;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwaggerParser extends RESTParser {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public RESTServiceDefinition parseDocForRestServiceDefinition(String document, IAPIProxySettingsEndpoint endpoint, String objectsPackageName) throws Exception {
        LOGGER.debug("starting parseDocForRestServiceDefinition()");
        RESTServiceDefinition restServiceDefinition = new RESTServiceDefinition(objectsPackageName);
        Gson gson = new GsonBuilder().create();
        JsonElement documentElement = gson.fromJson(document, JsonElement.class);
        JsonObject documentObject = documentElement.getAsJsonObject();
        ProxyConfig proxyConfig = new ProxyConfig();
        JsonElement swaggerElement = documentObject.get("swagger");
//        if (swaggerElement != null) {
//            proxyDefinition.Swagger = swaggerElement.getAsString();
//        } else {
//
//        }

//        LOGGER.debug("proxyDefinition REST is " + proxyDefinition.Swagger);
        JsonElement infoElement = documentObject.get("info");
        if (infoElement != null) {
            JsonElement descriptionElement = ((JsonObject)infoElement).get("description");
            proxyConfig.Description = descriptionElement != null ? descriptionElement.toString() : null;
            proxyConfig.Title = ((JsonObject)infoElement).get("title").getAsString();
            JsonElement versionElement = ((JsonObject)infoElement).get("version");
            proxyConfig.Version = versionElement != null ? versionElement.toString() : null;
        }

        LOGGER.debug("proxyDefinition Description is " + proxyConfig.Description);
        LOGGER.debug("proxyDefinition Title is " + proxyConfig.Title);
        LOGGER.debug("proxyDefinition Version is " + proxyConfig.Version);
        JsonElement hostElement = ((JsonObject)infoElement).get("host");
        if (hostElement != null) {
            proxyConfig.Host = hostElement.toString();
        } else {
            proxyConfig.Host = endpoint.getHost();
        }

        LOGGER.debug("proxyDefinition Host is " + proxyConfig.Host);
        JsonElement basePathElement = documentObject.get("basePath");
        if (basePathElement != null) {
            proxyConfig.BasePath = basePathElement.getAsString();
        } else {
            proxyConfig.BasePath = endpoint.getBasePath();
        }

        LOGGER.debug("proxyDefinition BasePath is " + proxyConfig.BasePath);
        List<String> schemeList = new ArrayList<String>();
        JsonElement schemesElement = documentObject.get("schemes");
        if (schemesElement != null) {
            for (JsonElement schemeElement : (JsonArray)schemesElement) {
                String scheme = schemeElement.getAsString();
                schemeList.add(scheme);
            }
            
            proxyConfig.Schemes = schemeList.toArray(new String[0]);
        } else {
            LOGGER.warn("no schemes specified");
        }

        proxyConfig.BaseProxyClass = endpoint.getBaseProxyClass();
        HashMap<String, ProxyDefinition> proxyDefinitionHashMap = parsePathsForProxyDefinitions(documentObject, proxyConfig, endpoint.getParseOperationIdForProxyName());
        restServiceDefinition.ProxyClasses = proxyDefinitionHashMap;
        List<ClassDefinition> classDefinitionList = parseDefinitionsForClassDefinitions(documentObject, proxyConfig);
        restServiceDefinition.Classes = classDefinitionList;
        LOGGER.debug("finishing parseDocForRestServiceDefinition()");
        return restServiceDefinition;
    }

    @Override
    public ProxyDefinition parseDoc(String document, IAPIProxySettingsEndpoint endpoint) throws Exception {
        LOGGER.debug("starting parseDoc()");
        Gson gson = new GsonBuilder().create();
        JsonElement documentElement = gson.fromJson(document, JsonElement.class);
        JsonObject documentObject = documentElement.getAsJsonObject();
        ProxyDefinition proxyDefinition = new ProxyDefinition();
        JsonElement swaggerElement = documentObject.get("swagger");
        if (swaggerElement != null) {
            proxyDefinition.Swagger = swaggerElement.getAsString();
        } else {

        }

        LOGGER.debug("proxyDefinition REST is " + proxyDefinition.Swagger);
        JsonElement infoElement = documentObject.get("info");
        if (infoElement != null) {
            JsonElement descriptionElement = ((JsonObject)infoElement).get("description");
            proxyDefinition.Description = descriptionElement != null ? descriptionElement.toString() : null;
            proxyDefinition.Title = ((JsonObject)infoElement).get("title").getAsString();
            JsonElement versionElement = ((JsonObject)infoElement).get("version");
            proxyDefinition.Version = versionElement != null ? versionElement.toString() : null;
        }

        LOGGER.debug("proxyDefinition Description is " + proxyDefinition.Description);
        LOGGER.debug("proxyDefinition Title is " + proxyDefinition.Title);
        LOGGER.debug("proxyDefinition Version is " + proxyDefinition.Version);
        JsonElement hostElement = ((JsonObject)infoElement).get("host");
        if (hostElement != null) {
            proxyDefinition.Host = hostElement.toString();
        } else {
            proxyDefinition.Host = endpoint.getHost();
        }

        LOGGER.debug("proxyDefinition Host is " + proxyDefinition.Host);
        JsonElement basePathElement = documentObject.get("basePath");
        if (basePathElement != null) {
            proxyDefinition.BasePath = basePathElement.getAsString();
        } else {
            proxyDefinition.BasePath = endpoint.getBasePath();
        }

        LOGGER.debug("proxyDefinition BasePath is " + proxyDefinition.BasePath);
        List<String> schemeList = new ArrayList<String>();
        JsonElement schemesElement = documentObject.get("schemes");
        if (schemesElement != null) {
            for (JsonElement schemeElement : (JsonArray)schemesElement) {
                String scheme = schemeElement.getAsString();
                schemeList.add(scheme);
            }
            
            proxyDefinition.Schemes = schemeList.toArray(new String[0]);
        } else {
            LOGGER.warn("no schemes specified");
        }

        proxyDefinition.BaseProxyClass = endpoint.getBaseProxyClass();
        this.parsePaths(documentObject, proxyDefinition, endpoint.getParseOperationIdForProxyName());
        this.parseDefinitions(documentObject, proxyDefinition);
        LOGGER.debug("finishing parseSwaggerDoc()");
        return proxyDefinition;
    }
    
    private HashMap<String, ProxyDefinition> parsePathsForProxyDefinitions(JsonObject documentObject, ProxyConfig proxyConfig, boolean parseOperationIdForProxyName) {
        LOGGER.debug("starting parsePathsForProxyDefinitions()");
        HashMap<String, ProxyDefinition> proxyDefinitionHashMap = new HashMap<String, ProxyDefinition>();
        List<Operation> pathOperationList = createOperationListFromJsonObject(documentObject, parseOperationIdForProxyName);
        LOGGER.debug("created pathOperationList");
        for (Operation operation : pathOperationList) {
            String operationProxyName = operation.ProxyName;
            if (proxyDefinitionHashMap.containsKey(operationProxyName)) {
                proxyDefinitionHashMap.get(operationProxyName).Operations.add(operation);
            } else {
                ProxyDefinition proxyDefinition = new ProxyDefinition(proxyConfig);
                proxyDefinition.Operations.add(operation);
                proxyDefinitionHashMap.put(operationProxyName, proxyDefinition);
            }
        }
        
//        LOGGER.debug("finishing parsePaths()");
        return proxyDefinitionHashMap;
    }

    private void parsePaths(JsonObject jObject, ProxyDefinition proxyDefinition, boolean parseOperationIdForProxyName) {
        LOGGER.debug("starting parsePaths()");
        List<Operation> pathOperationList = createOperationListFromJsonObject(jObject, parseOperationIdForProxyName);
        LOGGER.debug("created pathOperationList");
        for (Operation operation : pathOperationList) {
            proxyDefinition.Operations.add(operation);
        }
        
//        LOGGER.debug("finishing parsePaths()");
    }

    private List<Operation> createOperationListFromJsonObject(JsonObject jObject, boolean parseOperationIdForProxyName) {
//        LOGGER.debug("starting createOperationListFromJsonObject()");
        List<Operation> operationList = new ArrayList<Operation>();
        JsonObject pathsJsonObject = (JsonObject) jObject.get("paths");
//        LOGGER.debug("got pathsJsonObject");
        Set<Map.Entry<String, JsonElement>> pathsSet = pathsJsonObject.entrySet();
        for (Map.Entry<String, JsonElement> pathEntry : pathsSet) {
            List<Operation> pathOperationList = createOperationListFromPathElement(pathEntry, parseOperationIdForProxyName);
            for (Operation operation : pathOperationList) {
                operationList.add(operation);
            }
        }

        return operationList;
    }

    private List<Operation> createOperationListFromPathElement(Map.Entry<String, JsonElement> pathEntry, boolean parseOperationIdForProxyName) {
//        LOGGER.debug("starting createOperationListFromJsonObject()");
        List<Operation> pathOperationList = new ArrayList<Operation>();
        String path = pathEntry.getKey();
//        LOGGER.debug("path is " + path);
        JsonObject pathElement = (JsonObject)pathEntry.getValue();
        Set<Map.Entry<String, JsonElement>> operationsSet = pathElement.entrySet();
        for (Map.Entry<String, JsonElement> operationEntry : operationsSet) {
            Operation operation = createOperationFromOperationElement(operationEntry, path, parseOperationIdForProxyName);
//            LOGGER.debug("created operation");
            pathOperationList.add(operation);
        }

        return pathOperationList;
    }

    private Operation createOperationFromOperationElement(Map.Entry<String, JsonElement> operationEntry, String path, boolean parseOperationIdForProxyName) {
//        LOGGER.debug("starting createOperationFromOperationElement()");
        String method = operationEntry.getKey();
        LOGGER.debug("method is " + method);
        JsonObject operationObject = (JsonObject)operationEntry.getValue();
        String operationId = operationObject.get("operationId").getAsString();
        LOGGER.debug("operationId is " + operationId);
        String proxyName = "";
        if (parseOperationIdForProxyName) {
            if (operationId.contains("_")) {
                int underscoreLocation = operationId.indexOf("_");
                proxyName = operationId.substring(0, underscoreLocation);
                operationId = operationId.substring(underscoreLocation + 1);
            }
        }

        if (proxyName == null || proxyName.equals("")) {
            /* Did not get the proxy name from the operation id, so take the first tag value */
            JsonElement tagsElement = operationObject.get("tags");
            if (tagsElement != null) {
                JsonArray tagsArray = tagsElement.getAsJsonArray();
                List<String> tags = new ArrayList<String>();
                for (JsonElement tagElement : tagsArray) {
                    tags.add(tagElement.getAsString());
                }
                
                String firstTag = tags.get(0);
                proxyName = fixTypeName(firstTag);
            }
        }

        JsonElement descriptionElement = operationObject.get("description");
        String description = null;
        if (descriptionElement != null) {
            description = descriptionElement.toString();
        }

        String returnType;
        JsonElement twoHundredElement = ((JsonObject)operationObject.get("responses")).getAsJsonObject("200");
        if (twoHundredElement != null) {
            JsonElement okElement = ((JsonObject)twoHundredElement).getAsJsonObject();
            if (okElement != null) {
                boolean dummyNullable;
                returnType = getTypeName(okElement);
                if (returnType != null && returnType.equals("Void")) {
                    returnType = null;
                } else {

                }
            }
            else {
                returnType = null;
            }
        } else {
            returnType = null;
        }

        List<Parameter> parameters = new ArrayList<Parameter>();
        JsonElement parametersElement = operationObject.get("parameters");
        if (parametersElement != null) {
            for (JsonElement parameterElement : (JsonArray)parametersElement) {
                Parameter parameter = createParameterFromJsonElement((JsonObject)parameterElement);
//                LOGGER.debug("created parameter");
                parameters.add(parameter);
            }
        }

        Operation operation = new Operation(returnType, method, path, parameters, operationId, description, proxyName);
//        LOGGER.debug("created operation");
        return operation;
    }

    private Parameter createParameterFromJsonElement(JsonObject parameterElement) {
//        LOGGER.debug("starting createParameterFromJsonElement()");
        TypeDefinition type = parseType(parameterElement);
//        LOGGER.debug("parsed type " + type.Name);
        boolean isRequired = false;
        if (parameterElement.get("required") != null) {
            isRequired = parameterElement.get("required").getAsBoolean();
//            LOGGER.debug("isRequired is " + isRequired);
        }

        ParameterIn parameterIn = ParameterIn.Body;
        if (parameterElement.get("in") != null) {
            if (parameterElement.get("in").getAsString().equals("path")) {
//                LOGGER.debug("in is path");
                parameterIn = ParameterIn.Path;
            }
            else if (parameterElement.get("in").getAsString().equals("body")) {
//                LOGGER.debug("in is body");
                parameterIn = ParameterIn.Body;
            }
            else if (parameterElement.get("in").getAsString().equals("query")) {
//                LOGGER.debug("in is query");
                parameterIn = ParameterIn.Query;
            }
            else if (parameterElement.get("required").getAsString().equals("formData")) {
//                LOGGER.debug("in is formData");
                parameterIn = ParameterIn.FormData;
            }
        }

        JsonElement parameterDescriptionElement = parameterElement.get("description");
        String propDescription = "";
        if (parameterDescriptionElement != null) {
            propDescription = parameterDescriptionElement.getAsString();
        }

//        LOGGER.debug("propDescription is " + propDescription);
        String collectionFormat = "";
        JsonElement collectionFormatElement = parameterElement.get("collectionFormat");
        if (collectionFormatElement != null) {
            collectionFormat = collectionFormatElement.getAsString();
        }

//        LOGGER.debug("about to create parameter");
        Parameter parameter = new Parameter(type, parameterIn, isRequired, propDescription, collectionFormat);
//        LOGGER.debug("created parameter");
        return parameter;
    }
    
    private List<ClassDefinition> parseDefinitionsForClassDefinitions(JsonObject documentObject, ProxyConfig proxyConfig)
    {
//        LOGGER.debug("starting parseDefinitionsForClassList()");
        List<ClassDefinition> classDefinitionList = new ArrayList<ClassDefinition>();
        JsonObject definitionsJsonObject = (JsonObject) documentObject.get("definitions");
//        LOGGER.debug("got definitionsJsonObject");
        Set<Map.Entry<String, JsonElement>> definitionsSet = definitionsJsonObject.entrySet();
//        LOGGER.debug("got definitionsSet");
        for (Map.Entry<String, JsonElement> definitionEntry : definitionsSet) {
            boolean addIt = true;
            String className = definitionEntry.getKey();
            LOGGER.debug("className is " + className);
            ClassDefinition classDefinition = new ClassDefinition(className);
            JsonObject definitionObject = (JsonObject) definitionEntry.getValue();
//            LOGGER.debug("got definitionObject");
            JsonElement allOfElement = definitionObject.get("allOf");
            if (allOfElement != null) {
//                LOGGER.debug("allOfElement is not null");
                for (JsonElement itemElement : (JsonArray) allOfElement) {
                    JsonElement refTypeElement = definitionObject.get("$ref");
                    if (refTypeElement != null) {
//                        LOGGER.debug("refTypeElement is not null");
                        String inheritsType = refTypeElement.toString();
                        if (inheritsType.startsWith("#/definitions/")) {
                            inheritsType = inheritsType.replace("#/definitions/", "");
                        }

//                        LOGGER.debug("inheritsType is  + inheritsType");
                        classDefinition.Inherits = inheritsType;
                    }

                    JsonElement propertiesElement = ((JsonObject)itemElement).get("properties");
                    if (propertiesElement != null) {
//                        LOGGER.debug("properties is not null");
                        if (propertiesElement instanceof JsonArray) {
//                            LOGGER.debug("properties instance of JsonArray");
                            for (JsonElement propertyElement : (JsonArray)propertiesElement) {
                                TypeDefinition type = parseType(propertyElement);
                                classDefinition.Properties.add(type);
                            }
                        } else {
//                          LOGGER.debug("properties instance of JsonElement");
                            Set<Map.Entry<String, JsonElement>> propertiesSet = ((JsonObject)propertiesElement).entrySet();
                            for (Map.Entry<String, JsonElement> propertyMap : propertiesSet) {
                                String name = propertyMap.getKey();
                                JsonElement propertyElement = propertyMap.getValue();
                                TypeDefinition type = parseType(name, propertyElement);
                                classDefinition.Properties.add(type);                         
                            }
                        }
                    }
                }
            } else {
//                LOGGER.debug("allOfElement is null");
                JsonElement propertiesElement = definitionObject.get("properties");
                if (propertiesElement != null) {
//                    LOGGER.debug("propertiesElement is not null");
                    Set<Map.Entry<String, JsonElement>> propertiesSet = ((JsonObject)propertiesElement).entrySet();
                    for (Map.Entry<String, JsonElement> propertyMap : propertiesSet) {
                        String name = propertyMap.getKey();
                        JsonElement propertyElement = propertyMap.getValue();
                        TypeDefinition type = parseType(name, propertyElement);
                        classDefinition.Properties.add(type);
                    }
                } else {
                    /* TODO: Just because no properties, can still create the class */
                    //addIt = false;
                }
            }

            classDefinition.Name = fixGenericName(className);
            if (classDefinition.Name.equals("Void")) {
                addIt = false;
            }

            if (addIt) {
                classDefinitionList.add(classDefinition);
            }
        }

//        LOGGER.debug("finishing parseDefinitions()");
        return classDefinitionList;
    }

    private void parseDefinitions(JsonObject jObject, ProxyDefinition proxyDefinition)
    {
//        LOGGER.debug("starting parseDefinitions()");
        JsonObject definitionsJsonObject = (JsonObject) jObject.get("definitions");
//        LOGGER.debug("got definitionsJsonObject");
        Set<Map.Entry<String, JsonElement>> definitionsSet = definitionsJsonObject.entrySet();
//        LOGGER.debug("got definitionsSet");
        for (Map.Entry<String, JsonElement> definitionEntry : definitionsSet) {
            boolean addIt = true;
            String className = definitionEntry.getKey();
            LOGGER.debug("className is " + className);
            ClassDefinition classDefinition = new ClassDefinition(className);
            JsonObject definitionObject = (JsonObject) definitionEntry.getValue();
//            LOGGER.debug("got definitionObject");
            JsonElement allOfElement = definitionObject.get("allOf");
            if (allOfElement != null) {
//                LOGGER.debug("allOfElement is not null");
                for (JsonElement itemElement : (JsonArray) allOfElement) {
                    JsonElement refTypeElement = definitionObject.get("$ref");
                    if (refTypeElement != null) {
//                        LOGGER.debug("refTypeElement is not null");
                        String inheritsType = refTypeElement.toString();
                        if (inheritsType.startsWith("#/definitions/")) {
                            inheritsType = inheritsType.replace("#/definitions/", "");
                        }

//                        LOGGER.debug("inheritsType is  + inheritsType");
                        classDefinition.Inherits = inheritsType;
                    }

                    JsonElement propertiesElement = ((JsonObject)itemElement).get("properties");
                    if (propertiesElement != null) {
//                        LOGGER.debug("properties is not null");
                        if (propertiesElement instanceof JsonArray) {
//                            LOGGER.debug("properties instance of JsonArray");
                            for (JsonElement propertyElement : (JsonArray)propertiesElement) {
                                TypeDefinition type = parseType(propertyElement);
                                classDefinition.Properties.add(type);
                            }
                        } else {
//                          LOGGER.debug("properties instance of JsonElement");
                            Set<Map.Entry<String, JsonElement>> propertiesSet = ((JsonObject)propertiesElement).entrySet();
                            for (Map.Entry<String, JsonElement> propertyMap : propertiesSet) {
                                String name = propertyMap.getKey();
                                JsonElement propertyElement = propertyMap.getValue();
                                TypeDefinition type = parseType(name, propertyElement);
                                classDefinition.Properties.add(type);                         
                            }
                        }
                    }
                }
            } else {
//                LOGGER.debug("allOfElement is null");
                JsonElement propertiesElement = definitionObject.get("properties");
                if (propertiesElement != null) {
//                    LOGGER.debug("propertiesElement is not null");
                    Set<Map.Entry<String, JsonElement>> propertiesSet = ((JsonObject)propertiesElement).entrySet();
                    for (Map.Entry<String, JsonElement> propertyMap : propertiesSet) {
                        String name = propertyMap.getKey();
                        JsonElement propertyElement = propertyMap.getValue();
                        TypeDefinition type = parseType(name, propertyElement);
                        classDefinition.Properties.add(type);
                    }
                } else {
                    /* TODO: Just because no properties, can still create the class */
                    //addIt = false;
                }
            }

            classDefinition.Name = fixGenericName(className);
            if (classDefinition.Name.equals("Void")) {
                addIt = false;
            }

            if (addIt) {
                proxyDefinition.ClassDefinitions.add(classDefinition);
            }
        }

//        LOGGER.debug("finishing parseDefinitions()");
    }
    
    private TypeDefinition parseType(String name, JsonElement typeElement) {
//        LOGGER.debug("starting parseType()");
        boolean isNullable = false;
        JsonElement workingElement = typeElement;
//        LOGGER.debug("name is " + name);
        if (workingElement != null) {
//            LOGGER.debug("workingElement is not null");
            String typeName = getTypeName(workingElement);
//            LOGGER.debug("typeName is " + typeName);
            JsonElement enumElement = ((JsonObject)workingElement).get("enum");
            String[] enumValues = null;
            if (enumElement != null) {
//                LOGGER.debug("enumElement is not null");
                List<String> enumList = new ArrayList<String>();
                boolean anyRawNumbers = false;
//                LOGGER.debug("about to process enumElement");
                for (JsonElement enumValueElement : (JsonArray)enumElement) {
                    String enumValue = enumValueElement.getAsString();
                    try {
                        Double.parseDouble(enumValue);
                        anyRawNumbers = true;
                    }
                    catch (Exception e) {
                        LOGGER.warn("exception parsing as Double");
                    }
                    
                    enumList.add(enumValue);
                }

                if (anyRawNumbers == false) {
                    enumValues = enumList.toArray(new String[0]);
                    typeName = fixTypeName(name + "Values");
                }
            }

//            LOGGER.debug("typeName is " + typeName);
            typeName = fixGenericName(typeName);
//            LOGGER.debug("typeName is " + typeName);
            TypeDefinition type = new TypeDefinition(typeName, name, enumValues, isNullable);
            return type;
        }
        else {
            return null;
        }
    }    

    private TypeDefinition parseType(JsonElement typeElement) {
//        LOGGER.debug("starting parseType()");
        boolean isNullable = false;
        JsonElement workingElement;
        String name;
        if (typeElement instanceof JsonObject) {
//            LOGGER.debug("typeElement instanceof JsonObject");
            workingElement = typeElement;
            JsonElement nameElement = ((JsonObject)workingElement).get("name");
//            LOGGER.debug("got nameElement");
            name = nameElement.getAsString();
//            LOGGER.debug("name is " + name);
        } else {
//            LOGGER.debug("typeElement not instanceof JsonObject");
            workingElement = typeElement;
            if (typeElement instanceof JsonObject) {
                name = ((JsonObject)typeElement).get("name").getAsString();
            } else {
                name = "";
            }
        }

        LOGGER.debug("name is " + name);
        if (workingElement != null) {
//            LOGGER.debug("workingElement is not null");
            String typeName = getTypeName(workingElement);
//            LOGGER.debug("typeName is " + typeName);
            JsonElement enumElement = ((JsonObject)workingElement).get("enum");
            String[] enumValues = null;
            if (enumElement != null) {
                LOGGER.debug(" is not null");
                List<String> enumList = new ArrayList<String>();
                boolean anyRawNumbers = false;
//                LOGGER.debug("about to process enumElement");
                for (JsonElement enumValueElement : (JsonArray)enumElement) {
                    String enumValue = enumValueElement.getAsString();
                    LOGGER.debug("enumValue is " + enumValue);
                    try {
                        Double.parseDouble(enumValue);
                        anyRawNumbers = true;
                    }
                    catch (Exception e) {
                        LOGGER.debug("exception parsing as Double");
                    }
                    
                    enumList.add(enumValue);
                }

                if (anyRawNumbers == false) {
                    enumValues = enumList.toArray(new String[0]);
                    typeName = fixTypeName(name + "Values");
                }
            }

//            LOGGER.debug("typeName is " + typeName);
            typeName = fixGenericName(typeName);
//            LOGGER.debug("typeName is " + typeName);
            TypeDefinition type = new TypeDefinition(typeName, name, enumValues, isNullable);
            return type;
        }
        else {
            return null;
        }
    }

    private String parseRef(String input) {
        return input.startsWith("#/definitions/") ? input.substring("#/definitions/".length()) : input;
    }

    private String getTypeName(JsonElement classElement) {
//        LOGGER.debug("starting getTypeName()");
        if (classElement != null) {
            JsonElement refElement = ((JsonObject)classElement).get("$ref");
            boolean hasNullFlag = false;
            if (refElement != null) {
//                LOGGER.debug("refTypeElement is not null");
                String refType = refElement.getAsString();
//                LOGGER.debug("refTypeElement is " + refTypeElement);
                return fixTypeName(parseRef(refType));
            }

            JsonElement schemaElement = ((JsonObject)classElement).get("schema");
            if (schemaElement != null) {
//                LOGGER.debug("okElement is not null");
                String schemaTypeName = getTypeName(schemaElement);
//                LOGGER.debug("schemaTypeName is " + schemaTypeName);
                String fixedTypeName = fixTypeName(schemaTypeName);
//                LOGGER.debug("fixedTypeName is " + fixedTypeName);
                return fixedTypeName;
            }
            
            JsonElement nullableElement = ((JsonObject)classElement).get("x-nullable");
            if (nullableElement != null) {
//                LOGGER.debug("nullableElement is not null");
                hasNullFlag = true;
            }

            String type = null;
            JsonElement typeElement = ((JsonObject)classElement).getAsJsonPrimitive("type");
            if (typeElement != null) {
                type = typeElement.getAsString();
                if (type == null) {
//                    LOGGER.debug("type is null");
                    return null;
                }
            } else {
                return null;
            }
            
//            LOGGER.debug("type is " + type);
            if (type.equals("array")) {
//                LOGGER.debug("type is array");
                JsonElement itemsElement = ((JsonObject)classElement).get("items");
                boolean throwawayNullable;
                /* Temporary change: return array rather than List */
                String itemType = getTypeName(itemsElement);
                return itemType + "[]";
            }

            if (type.equals("boolean")) {
//                LOGGER.debug("type as string is boolean");
                return (hasNullFlag) ? "bool?" : "bool";
            }

            if (type.equals("file")) {
//                LOGGER.debug("type as string is file");
                return "file";
            }

            if (type.equals("string")) {
//                LOGGER.debug("type as string is string");
                JsonElement format = ((JsonObject)classElement).get("format");
                if (format == null) {
//                    LOGGER.debug("format is null");
                    return "string";
                }

                if (format.getAsString().equals("date") || format.getAsString().equals("date-time")) {
//                    LOGGER.debug("format as string is date");
                    return (hasNullFlag) ? "DateTime?" : "DateTime";
                }

                if (format.getAsString().equals("byte") || format.getAsString().equals("binary")) {
//                    LOGGER.debug("format as string is byte");
                    return (hasNullFlag) ? "byte[]" : "byte[]";
                }

                return "string";
            }

            if (type.equals("integer")) {
//                LOGGER.debug("type as string is integer");
                JsonElement format = ((JsonObject)classElement).get("format");
                if (format != null) {
                    if (format.toString().equals("int32"))
                        return (hasNullFlag) ? "int?" : "int";

                    if (format.toString().equals("int64"))
                        return (hasNullFlag) ? "long?" : "long";
                }

                return "int";
            }

            if (type.equals("number")) {
//                LOGGER.debug("type as string is number");
                JsonElement format = ((JsonObject)classElement).get("format");
                if (format != null) {
                    if (format.toString().equals("float"))
                        return (hasNullFlag) ? "float?" : "float";

                    if (format.toString().equals("double"))
                        return (hasNullFlag) ? "double?" : "double";

                    if (format.toString().equals("decimal"))
                        return (hasNullFlag) ? "decimal?" : "decimal";
                }

                return (hasNullFlag) ? "float?" : "float";
            }

            if (type.equals("object")) {
//                LOGGER.debug("type as string is object");
                return "object";
            }

            return "";
        } else {
            return "";
        }
    }
}

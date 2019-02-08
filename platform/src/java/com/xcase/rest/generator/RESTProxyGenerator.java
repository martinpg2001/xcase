package com.xcase.rest.generator;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import java.io.File;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RESTProxyGenerator implements IProxyGenerator {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static String objectsPackageName;
    public static String objectsPath;
    public static String packageName;
    public static HashMap<IAPIProxySettingsEndpoint, String> endpointHashMap = new HashMap<IAPIProxySettingsEndpoint, String>();
    public static int textPadding;
    
    public abstract RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint[] endpoints) throws Exception;
    public abstract RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint endpoint, String document, String username, String password, String tenant) throws Exception;
    public abstract RESTServiceDefinition generateSourceString(IAPIProxySettingsEndpoint endpoint, String document) throws Exception;
    public abstract RESTServiceDefinition generateSourceString(String document) throws Exception;

    protected static StringBuilder writeInterfaceStringBuilderForProxy(ProxyDefinition proxyDefinition, String proxy, IAPIProxySettingsEndpoint endPoint, String methodNameAppend) {
        StringBuilder interfaceStringBuilder = new StringBuilder();
        writeHeaders(interfaceStringBuilder, objectsPackageName);
        writeLine(interfaceStringBuilder, "/*");
        writeLine(interfaceStringBuilder, "/// Interface for Web Proxy for " + proxy);
        writeLine(interfaceStringBuilder, "*/");
        String interfaceName = "I" + RESTParser.fixTypeName(proxy) + "WebProxy";
        writeLine(interfaceStringBuilder, "public interface " + interfaceName + " {");
        for (Operation operation : proxyDefinition.Operations) {
            if (operation.ProxyName.equals(proxy)) {
                String returnType = null;
                if (operation.ReturnType == null || operation.ReturnType.equals("")) {
                    returnType = "void";
                } else {
                    returnType = Operation.getCleanJavaReturnType(operation.ReturnType);
                }
                
                List<Parameter> parameterList = operation.Parameters;
                List<EnumDefinition> proxyParamEnums = new ArrayList<EnumDefinition>();
                for (Parameter parameter : parameterList) {
                    if (parameter.Type.EnumValues != null) {
                        parameter.Type.TypeName = operation.OperationId + parameter.Type.Name;
                        proxyParamEnums.add(new EnumDefinition(parameter.Type.TypeName, parameter.Type.EnumValues));
                    }
                }

                String parameters = "";
                List<String> parameterStringList = new ArrayList<String>();
                for (Parameter parameter : parameterList) {
                    if (parameter.IsRequired) {
                        String parameterString = getDefaultType(parameter) + " " + getDefaultTypeName(parameter);
                        parameterStringList.add(parameterString);
                    } else {
                        String parameterString = getDefaultType(parameter) + " " + getDefaultTypeName(parameter);
                        parameterStringList.add(parameterString);
                    }
                }
                
                parameters = String.join(", ", parameterStringList);
                String methodName = RESTParser.fixTypeName(operation.OperationId);
                String methodLine = returnType + " " + methodName + methodNameAppend + "(" + parameters + ");";
                writeLine(interfaceStringBuilder, methodLine);
            }
        }
        
        writeLine(interfaceStringBuilder, "}");
        writeLine(interfaceStringBuilder);
//        LOGGER.debug("about to write proxy interface " + interfaceName + ".java");
        File interfaceFile = new File("src/java/" + objectsPath, interfaceName + ".java");
        try (PrintWriter printWriter = new PrintWriter(interfaceFile, "UTF-8")) {
            printWriter.write(interfaceStringBuilder.toString());
        } catch (Exception e) {
        } finally {
        }
        
        return interfaceStringBuilder;
    }

    protected static StringBuilder writeProxyStringBuilderForProxy(ProxyDefinition proxyDefinition, String proxy, IAPIProxySettingsEndpoint endPoint, String methodNameAppend) {
//        LOGGER.debug("starting writeProxyStringBuilderForProxy()");
        proxyDefinition.Name = RESTParser.fixTypeName(proxy) + "WebProxy";
        StringBuilder proxyStringBuilder = proxyDefinition.writeToStringBuilder(proxy, objectsPackageName, methodNameAppend);
        File proxyFile = new File("src/java/" + objectsPath, proxyDefinition.Name + ".java");
        try (final PrintWriter printWriter = new PrintWriter(proxyFile, "UTF-8")) {
            printWriter.write(proxyStringBuilder.toString());
        } catch (Exception e) {
        } finally {
        }
        
        return proxyStringBuilder;
    }
    
    private static String defaultReturnValue(String returnType) {
        if (returnType == "int") {
            return "-1";
        }

        return "null";
    }

    static String getDefaultType(Parameter parameter) {
        if (parameter.Type != null) {
            String typeName = parameter.Type.TypeName;
            if (typeName == "file") {
                return "Map<String, byte[]>";
            }
            if (parameter.Type.IsNullableType) {
                //typeName += "?";
            }

            /* Ugly hack to manage parameters of Array type */
            String output = ((parameter.CollectionFormat == "multi") && !typeName.endsWith("[]")) ? typeName + "[]" : typeName;
            /* Ugly hack and will not work if type is both array and contains [ or ] */
            if (!output.contains("[]")) {
                output = RESTParser.fixTypeName(output);
            } else {
//              LOGGER.debug("output contains [] " + output);
            }
            
            if (output == "bool") {
                output = "boolean";
            }
            
            if (output == "DateTime") {
                output = "Date";
            }
            
            if (output == "int[][]") {
                output = "int[]";
            }
            
            if (output == "long?") {
                output = "long";
            }
            
            if (output == "string") {
                output = "String";
            }
            
            if (output.equals("string[]")) {
                output = "String[]";
            }
            
            if (output == "string[][]") {
                output = "String[]";
            }
            
            return output;
        } else {
            return "String";
        }
    }

    protected static String getDefaultTypeName(Parameter x) {
        if (x.Type.TypeName.endsWith("Data") || x.Type.TypeName.endsWith("Data[]")) {
            /* TODO: do we need to do this?
            return "body";
             */
        }
        
        return x.Type.getCleanJavaTypeName();
    }

    protected static String getDefaultValue(Parameter x) {
        if (!x.Type.IsNullableType && x.CollectionFormat != "multi" && x.Type.EnumValues != null) {
            return getDefaultType(x) + "." + x.Type.EnumValues[0];
        }
        
        return "null";
    }

    protected static boolean isIntrinsicType(String typeName) {
        switch (typeName.toLowerCase()) {
            case "bool":
            case "boolean":
            case "byte":
            case "enum":
            case "int":
            case "long":
            case "DateTime":
            case "float":
            case "double":
                return true;
            default:
                return false;
        }
    }

    protected static void writeHeaders(StringBuilder stringBuilder, String packageString) {
        writeLine(stringBuilder, "package " + packageString + ";");
        writeLine(stringBuilder);
        writeImportLine(stringBuilder, "java.io.*");
        writeImportLine(stringBuilder, "java.net.*");
        writeImportLine(stringBuilder, "java.util.*");
        writeImportLine(stringBuilder, "org.apache.commons.lang3.*");
        writeImportLine(stringBuilder, "org.apache.http.*");
        writeImportLine(stringBuilder, "org.apache.http.message.*");
        writeImportLine(stringBuilder, "org.apache.logging.log4j.*");
        writeImportLine(stringBuilder, "com.google.gson.*");
        writeImportLine(stringBuilder, "com.xcase.common.impl.simple.core.CommonHTTPManager");
        writeImportLine(stringBuilder, "com.xcase.common.utils.URLUtils");
        writeImportLine(stringBuilder, "com.xcase.rest.generator.*");
        writeImportLine(stringBuilder, "com.xcase.rest.generator.swagger.*");
        writeLine(stringBuilder);
    }

    protected static void printLogger(StringBuilder stringBuilder) {
        writeLine(stringBuilder);
        writeLine(stringBuilder, "protected static final Logger LOGGER = LogManager.getLogger();");
        writeLine(stringBuilder);
    }

    protected static void printLogger(StringBuilder stringBuilder, String className) {
        writeLine(stringBuilder);
        writeLine(stringBuilder, "protected static final Logger LOGGER = LogManager.getLogger(" + className + ".class);");
        writeLine(stringBuilder);
    }
    
    private static String reformat(String description) {
        if (description == null) {
            return "";
        } else {
            return description.replace("\n", " ").replace("\r", " ").replaceAll(" +", " ");
        }
    }

    protected static boolean toStringIsValid(Parameter parameter) {
        if (parameter.Type.EnumValues != null) {
            return false;
        }
        
        String type = parameter.Type.getCleanJavaTypename();
        if (type != null) {
            if (type == "boolean") {
                return false;
            }
            
            if (type == "int") {
                return false;
            }
            
            if (type == "int[]") {
                return false;
            }
            
            if (type == "long") {
                return false;
            }
        }
        
        return true;
    }

    protected static StringBuilder writeClassDefinitionToStringBuilder(ClassDefinition classDefinition, IAPIProxySettingsEndpoint endPoint) {
        String modelClassName = classDefinition.getCleanClassName();
        StringBuilder classStringBuilder = classDefinition.writeToStringBuilder(objectsPackageName);
//        StringBuilder modelClassStringBuilder = new StringBuilder();
//        List<Enum> modelEnums = new ArrayList<Enum>();
//        String modelClassName = classDefinition.getCleanClassName();
//        writeHeaders(modelClassStringBuilder, objectsPackageName);
//        String inheritsClass = "";
//        if (classDefinition.Inherits != null) {
//            inheritsClass = " extends " + classDefinition.Inherits;
//        }
//        
//        writeLine(modelClassStringBuilder, "public class " + modelClassName + inheritsClass + " {");
//        for (TypeDefinition typeDefinition : classDefinition.Properties) {
//            String typeName = typeDefinition.getCleanJavaTypename();
//            if (typeName == null || typeName.equals("")) {
//                typeName = "object";
//            } else {
//                typeName = typeDefinition.getCleanJavaTypename();
//            }
//            
//            writeLine(modelClassStringBuilder, "public " + typeName + " " + RESTParser.fixJavaTypeName(typeDefinition.Name) + ";");
//            if (typeDefinition.EnumValues != null) {
////                LOGGER.debug("typeDefinition EnumValues is not null");
//                modelEnums.add(new EnumDefinition(typeName, typeDefinition.EnumValues));
//            }
//        }
//        
//        for (EnumDefinition modelEnum : modelEnums) {
//            writeLine(modelClassStringBuilder, "public enum " + RESTParser.fixJavaTypeName(modelEnum.Name));
//            writeLine(modelClassStringBuilder, "{");
//            for (String value : modelEnum.Values) {
//                writeLine(modelClassStringBuilder, RESTParser.fixTypeName(value) + "(\"" + value + "\"),");
//            }
//            
//            writeLine(modelClassStringBuilder, ";");
//            writeLine(modelClassStringBuilder);
//            writeLine(modelClassStringBuilder, "private String description;");
//            writeLine(modelClassStringBuilder, RESTParser.fixTypeName(modelEnum.Name) + "(String description) {");
//            writeLine(modelClassStringBuilder, "this.description = description;");
//            writeLine(modelClassStringBuilder, "}");
//            writeLine(modelClassStringBuilder, "}");
//            writeLine(modelClassStringBuilder);
//        }
//        
//        writeLine(modelClassStringBuilder, "}");
//        writeLine(modelClassStringBuilder);
        File modelClassFile = new File("src/java/" + objectsPath, modelClassName + ".java");
        try (PrintWriter printWriter = new PrintWriter(modelClassFile, "UTF-8")) {
            printWriter.write(classStringBuilder.toString());
        } catch (Exception e) {
        } finally {
        }
        
        return classStringBuilder;
    }

    protected static void writeImportLine(StringBuilder stringBuilder, String importString) {
        writeLine(stringBuilder, "import " + importString + ";");
    }

    protected static void writeMethod(Operation operation, StringBuilder proxyStringBuilder, String method) {
//        LOGGER.debug("starting WriteMethod()");
        String returnType = null;
        if (operation.ReturnType == null || operation.ReturnType.equals("")) {
            returnType = "void";
        } else {
            returnType = Operation.getCleanJavaReturnType(operation.ReturnType);
        }
        
        List<Parameter> parameterList = operation.Parameters;
        writeLine(proxyStringBuilder, "String entityBody = null;");
        writeLine(proxyStringBuilder, "Gson gson = null;");
        for (Parameter parameter : parameterList) {
            if (parameter.ParameterIn == ParameterIn.Body) {
                if (method != "GET") {
//                    LOGGER.debug("bodyParameter is not null and method is " + method);
                    if (parameter != null) {
                        String bodyParameterTypeName = getDefaultTypeName(parameter);
//                        LOGGER.debug("bodyParameterTypeName is " + bodyParameterTypeName);
                        writeLine(proxyStringBuilder, "gson = new GsonBuilder().setDateFormat(\"yyyy-MM-dd' 'HH:mm:ss\").create();");
                        writeLine(proxyStringBuilder, "entityBody = gson.toJson(" + bodyParameterTypeName + ");");
                    }
                    
                    writeLine(proxyStringBuilder, "LOGGER.debug(\"entityBody is \" + entityBody);");
                }
            }
        }
        
        /* Process form parameters */
        writeLine(proxyStringBuilder, "HashMap<String, byte[]> fileParametersHashMap = new HashMap<String, byte[]>();");
        writeLine(proxyStringBuilder, "List<NameValuePair> formKeyValuePairs = new ArrayList<NameValuePair>();");
        boolean hasFileParameters = false;
        for (Parameter parameter : parameterList) {
            if (parameter.ParameterIn == ParameterIn.FormData) {
                String formParameterTypeName = parameter.Type.Name;
                if (parameter.Type.TypeName != "file") {
                    if (parameter.IsRequired == false) {
                        writeIfNotNullStatementOpening(proxyStringBuilder, formParameterTypeName, parameter.Type.TypeName);
                        writeLine(proxyStringBuilder, "formKeyValuePairs.add(new BasicNameValuePair(\"" + formParameterTypeName + "\", " + formParameterTypeName + "));");
                        writeIfNotNullStatementClosing(proxyStringBuilder, formParameterTypeName, parameter.Type.TypeName);
                    } else {
                        writeLine(proxyStringBuilder, "formKeyValuePairs.add(new BasicNameValuePair(\"" + formParameterTypeName + "\", " + formParameterTypeName + "));");
                    }
                } else {
//                    LOGGER.debug("formParameter type is file");
                    hasFileParameters = true;
                    writeLine(proxyStringBuilder, "for (String key : " + formParameterTypeName + ".keySet()) {");
                    writeLine(proxyStringBuilder, "fileParametersHashMap.put(key, " + formParameterTypeName + ".get(key));");
                    writeLine(proxyStringBuilder, "}");
                    writeLine(proxyStringBuilder, "");
                }
            }
        }
        
        if (method != null) {
            switch (method) {
                case "DELETE":
                    writeLine(proxyStringBuilder, "JsonElement response = apiClient.doJsonDelete(url, headers, formKeyValuePairs, entityBody);");
                    break;
                case "GET":
                    writeLine(proxyStringBuilder, "JsonElement response = apiClient.doJsonGet(url, headers, formKeyValuePairs);");
                    break;
                case "PATCH":
                    writeLine(proxyStringBuilder, "JsonElement response = apiClient.doJsonPatch(url, headers, formKeyValuePairs, entityBody);");
                    break;
                case "POST":
                    if (!hasFileParameters) {
                        writeLine(proxyStringBuilder, "JsonElement response = apiClient.doJsonPost(url, headers, formKeyValuePairs, entityBody);");
                    } else {
                        writeLine(proxyStringBuilder, "String response = apiClient.doMultipartByteArrayPost(url, fileParametersHashMap, headers, formKeyValuePairs);");
                    }
                    break;
                case "PUT":
                    writeLine(proxyStringBuilder, "JsonElement response = apiClient.doJsonPut(url, headers, formKeyValuePairs, entityBody);");
                    break;
                default:
                    LOGGER.warn("unrecognized method " + method);
                    break;
            }
            
            if (operation.ReturnType != null) {
                writeLine(proxyStringBuilder, "Gson responseGson = new GsonBuilder().setDateFormat(\"yyyy-MM-dd' 'HH:mm:ss\").create();");
                writeLine(proxyStringBuilder, returnType + " entity = responseGson.fromJson(response, " + returnType + ".class);");
                writeLine(proxyStringBuilder, "return entity;");
            }
        }
    }

    static void writeIfNotNullStatementClosing(StringBuilder stringBuilder, String parameterName, String typeName) {
        writeLine(stringBuilder, "}");
    }

    static void writeIfNotNullStatementOpening(StringBuilder stringBuilder, String parameterName, String typeName) {
        if (isIntrinsicType(typeName)) {
            writeLine(stringBuilder, "if (true) {");
        } else {
            writeLine(stringBuilder, "if (" + parameterName + " != null) {");
        }
    }

    protected static void writeOperationToStringBuilder(Operation operation, StringBuilder proxyStringBuilder, List<EnumDefinition> proxyParamEnums, String methodNameAppend) {
//        LOGGER.debug("starting writeOperationToStringBuilder()");
        String returnType = null;
        if (operation.ReturnType == null || operation.ReturnType.equals("")) {
            returnType = "void";
        } else {
            returnType = Operation.getCleanJavaReturnType(operation.ReturnType);
        }
        
        List<Parameter> parameterList = operation.Parameters;
        for (Parameter parameter : parameterList) {
            if (parameter.Type.EnumValues != null) {
                parameter.Type.TypeName = operation.OperationId + parameter.Type.Name;
                proxyParamEnums.add(new EnumDefinition(parameter.Type.TypeName, parameter.Type.EnumValues));
            }
        }
        
        String parameters = "";
        List<String> parameterStringList = new ArrayList<String>();
        for (Parameter parameter : parameterList) {
//            LOGGER.debug("parameter type name is " + parameter.Type.Name);
            if (parameter.IsRequired) {
                String parameterString = getDefaultType(parameter) + " " + getDefaultTypeName(parameter);
                parameterStringList.add(parameterString);
            } else {
                String parameterString = getDefaultType(parameter) + " " + getDefaultTypeName(parameter);
                parameterStringList.add(parameterString);
            }
        }
        
        parameters = String.join(", ", parameterStringList);
        String methodName = RESTParser.fixTypeName(operation.OperationId);
        writeLine(proxyStringBuilder, "/// <summary>");
        String summary = operation.Description != null && !operation.Description.equals("") ? reformat(operation.Description) : "No description provided.";
        if (summary != null && !summary.equals("")) {
            writeLine(proxyStringBuilder, "///");
        } else {
            writeLine(proxyStringBuilder, "/// " + summary);
        }
        
        writeLine(proxyStringBuilder, "/// </summary>");
        for (Parameter parameter : operation.Parameters) {
            String parameterDescription = parameter.Description != null && !parameter.Description.equals("") ? reformat(parameter.Description) : "No description provided.";
            if (parameter.Type != null) {
                writeLine(proxyStringBuilder, "/// <param name=\"" + getDefaultTypeName(parameter) + "\">" + parameterDescription + "</param>");
            }
        }
        
        writeLine(proxyStringBuilder, "public " + returnType + " " + methodName + methodNameAppend + "(" + parameters + ") {");
        writeLine(proxyStringBuilder, "LOGGER.debug(\"starting " + methodName + "()\");");
        if (operation.Path.startsWith("/")) {
            writeLine(proxyStringBuilder, "String url = \"" + operation.Path.substring(1) + "\"");
        } else {
            writeLine(proxyStringBuilder, "String url = \"" + operation.Path + "\"");
        }
        
        for (Parameter parameter : parameterList) {
//            LOGGER.debug("parameter type name is " + parameter.Type.Name);
            if (parameter.ParameterIn == ParameterIn.Path) {
//                LOGGER.debug("ParameterIn is path");
                String cleanJavaTypeName = parameter.Type.getCleanJavaTypeName();
                if (toStringIsValid(parameter)) {
                    writeLine(proxyStringBuilder, ".replace(\"{" + cleanJavaTypeName + "}\", URLUtils.encodeUrl(" + cleanJavaTypeName + ".toString()))");
                } else {
                    writeLine(proxyStringBuilder, ".replace(\"{" + cleanJavaTypeName + "}\", URLUtils.encodeUrl(String.valueOf(" + cleanJavaTypeName + ")))");
                }
            }
        }
        
        writeLine(proxyStringBuilder, ";");
        writeLine(proxyStringBuilder);
        writeLine(proxyStringBuilder, "url = _baseUrl + url;");
        for (Parameter parameter : parameterList) {
            if (parameter.ParameterIn == ParameterIn.Query) {
//                LOGGER.debug("ParameterIn is query");
                String cleanJavaTypeName = parameter.Type.getCleanJavaTypeName();
                if (parameter.IsRequired == false && (parameter.Type.EnumValues == null)) {
                    writeIfNotNullStatementOpening(proxyStringBuilder, cleanJavaTypeName, parameter.Type.TypeName);
                }
                
                String toString = toStringIsValid(parameter) ? cleanJavaTypeName + ".toString()" : "String.valueOf(" + cleanJavaTypeName + ")";
                if (parameter.CollectionFormat != null) {
                    if (parameter.CollectionFormat.equalsIgnoreCase("csv")) {
                        writeLine(proxyStringBuilder, "url = appendQuery(url, \"" + parameter.Type.Name + "\", StringUtils.join(\",\", " + parameter.Type.getCleanJavaTypeName() + "));");
                    } else if (parameter.CollectionFormat.equalsIgnoreCase("ssv")) {
                        writeLine(proxyStringBuilder, "url = appendQuery(url, \"" + parameter.Type.Name + "\", StringUtils.join(\" \", " + parameter.Type.getCleanJavaTypeName() + "));");
                    } else if (parameter.CollectionFormat.equalsIgnoreCase("tsv")) {
                        writeLine(proxyStringBuilder, "url = appendQuery(url, \"" + parameter.Type.Name + "\", StringUtils.join(\"\t\", " + parameter.Type.getCleanJavaTypeName() + "));");
                    } else if (parameter.CollectionFormat.equalsIgnoreCase("pipes")) {
                        writeLine(proxyStringBuilder, "url = appendQuery(url, \"" + parameter.Type.Name + "\", StringUtils.join(\"|\", " + parameter.Type.getCleanJavaTypeName() + "));");
                    } else if (parameter.CollectionFormat.equalsIgnoreCase("multi")) {
                        if (toStringIsValid(parameter)) {
                            toString = "item.toString()";
                        } else {
                            toString = "String.valueOf(item)";
                        }
                        
                        writeLine(proxyStringBuilder, "for (" + getElementType(parameter) + " item : " + parameter.Type.getCleanJavaTypeName() + ") {");
                        writeLine(proxyStringBuilder, "url = appendQuery(url, \"" + parameter.Type.Name + "\", " + toString + ");");
                        writeLine(proxyStringBuilder, "}");
                    } else {
                        writeLine(proxyStringBuilder, "url = appendQuery(url, \"" + parameter.Type.Name + "\", " + toString + ");");
                    }
                } else {
                    writeLine(proxyStringBuilder, "url = appendQuery(url, \"" + parameter.Type.Name + "\", " + toString + ");");
                }
                
                if (parameter.IsRequired == false && (parameter.Type.EnumValues == null)) {
                    writeIfNotNullStatementClosing(proxyStringBuilder, parameter.Type.getCleanJavaTypeName(), parameter.Type.TypeName);
                }
                
                writeLine(proxyStringBuilder);
            }
        }
        
        writeLine(proxyStringBuilder);
        writeLine(proxyStringBuilder, "LOGGER.debug(\"url is \" + url);");
        writeLine(proxyStringBuilder, "try (CommonHTTPManager apiClient = buildHttpClient()) {");
        writeLine(proxyStringBuilder, "Header[] headers = setHeaders();");
        String method = operation.Method.toUpperCase();
//        LOGGER.debug("method is " + method);
        writeLine(proxyStringBuilder, "LOGGER.debug(\"method is " + method + "\");");
//        LOGGER.debug("about to write method " + method);
        writeMethod(operation, proxyStringBuilder, method);
//        LOGGER.debug("written method " + method);
        //writeLine(classStringBuilder, "EnsureSuccessStatusCodeAsync(response);");
        if (operation.ReturnType != null && !operation.ReturnType.equals("")) {
            /* Nothing to do here */
        }
        
        writeLine(proxyStringBuilder, "}"); // close up the try using
        writeLine(proxyStringBuilder, "catch (IOException ioe) {"); // catch IOException
        writeLine(proxyStringBuilder, "LOGGER.warn(\"ioexception invoking method: \" + ioe.getMessage());");
        writeLine(proxyStringBuilder, "}");
        writeLine(proxyStringBuilder, "catch (Exception e) {"); // catch Exception
        writeLine(proxyStringBuilder, "LOGGER.warn(\"exception invoking method: \" + e.getMessage());");
        writeLine(proxyStringBuilder, "}");
        writeLine(proxyStringBuilder);
        
        if (returnType != null && !returnType.equals("") && !returnType.equals("void")) {
            writeLine(proxyStringBuilder, "return " + defaultReturnValue(operation.ReturnType) + ";");
        }
        
        writeLine(proxyStringBuilder, "}"); // close up the method
        writeLine(proxyStringBuilder);
    }
    
    private static String getElementType(Parameter parameter) {
        return trimArray(getDefaultType(parameter));
    }
    
    public void getEndpointDoc(String requestUri, IAPIProxySettingsEndpoint endPoint) throws Exception {
//        LOGGER.debug("starting getEndpointSwaggerDoc()");
        CommonHTTPManager commonHTTPManager = CommonHTTPManager.refreshCommonHTTPManager();
        String swaggerString = commonHTTPManager.doStringGet(requestUri, null, null, null);
        if (swaggerString == null) {
            throw new Exception("Error downloading from: " + endPoint.getUrl());
        }

        LOGGER.debug("downloaded: " + requestUri);
        endpointHashMap.put(endPoint, swaggerString);
//        LOGGER.debug("finishing getEndpointSwaggerDoc()");
    }
    
    protected static String trimArray(String type) {
        if (type.endsWith("[]")) {
            return type.substring(0, type.length() - 2);
        }
        
        return type;
    }

    public static void writeLine(StringBuilder stringBuilder) {
        stringBuilder.append("\n");
    }

    public static void writeLine(StringBuilder stringBuilder, String text) {
        if (text.trim().endsWith("}") && textPadding != 0) {
            textPadding--;
        }

        char[] c = new char[textPadding * 4];
        Arrays.fill(c, ' ');
        String indent = new String(c);
        stringBuilder.append(indent + text + "\n");
        if (text.trim().endsWith("{")) {
            textPadding++;
        }
    }
}

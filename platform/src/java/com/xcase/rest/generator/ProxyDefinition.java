package com.xcase.rest.generator;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import static com.xcase.rest.generator.RESTProxyGenerator.objectsPath;
import static com.xcase.rest.generator.RESTProxyGenerator.writeHeaders;
import java.io.File;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProxyDefinition extends ClassDefinition implements IProxyDefinition {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public ProxyDefinition() {
        super();
        this.ClassDefinitions = new ArrayList<ClassDefinition>();
        this.Operations = new ArrayList<Operation>();        
    }
    
    public ProxyDefinition(String name) {
        super(name);
        this.ClassDefinitions = new ArrayList<ClassDefinition>();
        this.Operations = new ArrayList<Operation>();
    }
    
    public ProxyDefinition(ProxyConfig proxyConfig) {
        this.BasePath = proxyConfig.BasePath;
        this.BaseProxyClass = proxyConfig.BaseProxyClass;
        this.BaseURL = proxyConfig.BaseURL;
        this.Description = proxyConfig.Description;
        this.Host = proxyConfig.Host;
        this.Schemes = proxyConfig.Schemes;
        this.Swagger = proxyConfig.Swagger;
        this.Title = proxyConfig.Title;
        this.Version = proxyConfig.Version;
        this.ClassDefinitions = new ArrayList<ClassDefinition>();
        this.Operations = new ArrayList<Operation>();
    }    

    public String BasePath;
    public String BaseProxyClass;
    public String BaseURL;
    public String Description;
    public String Host;
    public String[] Schemes;
    public String Swagger;
    public String Title;
    public String Version;

    public List<ClassDefinition> ClassDefinitions;
    public List<Operation> Operations;
    
    public StringBuilder writeToStringBuilder(String proxy, String objectsPackageName, String methodNameAppend) {
//        LOGGER.debug("starting writeProxyStringBuilderForProxy()");
        String objectsPath = objectsPackageName.replace(".", "/");
        Name = RESTParser.fixTypeName(proxy) + "WebProxy";
        LOGGER.debug("proxy className is " + Name);
        StringBuilder interfaceStringBuilder = new StringBuilder();
        writeHeaders(interfaceStringBuilder, objectsPackageName);
        writeLine(interfaceStringBuilder, "/*");
        writeLine(interfaceStringBuilder, "/// Interface for Web Proxy for " + proxy);
        writeLine(interfaceStringBuilder, "*/");  
        writeLine(interfaceStringBuilder, "public interface I" + Name + " {");
        for (Operation operation : Operations) {
            writeOperationInterfaceToStringBuilder(operation, interfaceStringBuilder, methodNameAppend);
        }
        
        /* close interface def */
        writeLine(interfaceStringBuilder, "}");
        LOGGER.debug("about to write interface I" + Name + ".java");
        File interfaceFile = new File("src/java/" + objectsPath, "I" + Name + ".java");
        try (final PrintWriter printWriter = new PrintWriter(interfaceFile, "UTF-8")) {
            printWriter.write(interfaceStringBuilder.toString());
        } catch (Exception e) {
        } finally {
        }
        
        StringBuilder proxyStringBuilder = new StringBuilder();
        writeHeaders(proxyStringBuilder, objectsPackageName);
        writeLine(proxyStringBuilder, "/*");
        writeLine(proxyStringBuilder, "/// Web Proxy for " + proxy);
        writeLine(proxyStringBuilder, "*/");
        writeLine(proxyStringBuilder, "public class " + Name + " extends " + BaseProxyClass + " implements I" + Name + " {");
        writeLogger(proxyStringBuilder, Name);
        writeLine(proxyStringBuilder, "public " + Name + "(URL baseUrl) {");
        writeLine(proxyStringBuilder, "super(baseUrl);");
        writeLine(proxyStringBuilder, "}");
        writeLine(proxyStringBuilder);
        writeLine(proxyStringBuilder, "public " + Name + "(URL baseUrl, String token) {");
        writeLine(proxyStringBuilder, "super(baseUrl, token);");
        writeLine(proxyStringBuilder, "}");
        writeLine(proxyStringBuilder);
        List<EnumDefinition> proxyParamEnums = new ArrayList<EnumDefinition>();
        /* Async operations (Web methods) */
        for (Operation operation : Operations) {
            writeOperationToStringBuilder(operation, proxyStringBuilder, proxyParamEnums, methodNameAppend);
        }
        
        LOGGER.debug("finished operations");
        for (EnumDefinition proxyParamEnum : proxyParamEnums) {
            LOGGER.debug("next proxy enum " + proxyParamEnum.Name);
            StringBuilder enumStringBuilder = proxyParamEnum.writeToStringBuilder(proxy, objectsPackageName);
            File proxyEnumFile = new File("src/java/" + objectsPath, RESTParser.fixTypeName(proxyParamEnum.Name) + ".java");
            try (PrintWriter printWriter = new PrintWriter(proxyEnumFile, "UTF-8")) {
                printWriter.write(enumStringBuilder.toString());
            } catch (Exception e) {
            } finally {
            }
        }
        
        LOGGER.debug("finished enums");
        /* close class def */
        writeLine(proxyStringBuilder, "}");
        LOGGER.debug("closed proxy class");
        writeLine(proxyStringBuilder);
        LOGGER.debug("about to write proxy " + Name + ".java");
        File proxyFile = new File("src/java/" + objectsPath, Name + ".java");
        try (final PrintWriter printWriter = new PrintWriter(proxyFile, "UTF-8")) {
            printWriter.write(proxyStringBuilder.toString());
        } catch (Exception e) {
        } finally {
        }
        
        return proxyStringBuilder;
    }
    
    public void writeMethod(Operation operation, StringBuilder proxyStringBuilder, String method) {
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
    
    private void writeOperationInterfaceToStringBuilder(Operation operation, StringBuilder interfaceStringBuilder, String methodNameAppend) {
//        LOGGER.debug("starting writeOperationInterfaceToStringBuilder()");
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
            }
        }
        
        String parameters = "";
        List<String> parameterStringList = new ArrayList<String>();
        for (Parameter parameter : parameterList) {
            LOGGER.debug("parameter type name is " + parameter.Type.Name);
            if (parameter.IsRequired) {
                String parameterString = getDefaultType(parameter) + " " + getDefaultTypeName(parameter);
                parameterStringList.add(parameterString);
            } else {
                String parameterString = getDefaultType(parameter) + " " + getDefaultTypeName(parameter);
                parameterStringList.add(parameterString);
            }
        }
        
        parameters = String.join(", ", parameterStringList);
        LOGGER.debug("parameters is " + parameters);
        String methodName = RESTParser.fixTypeName(operation.OperationId);
        writeLine(interfaceStringBuilder, "public " + returnType + " " + methodName + methodNameAppend + "(" + parameters + ");");
//        LOGGER.debug("finishing writeOperationInterfaceToStringBuilder()");
    }
    
    private void writeOperationToStringBuilder(Operation operation, StringBuilder proxyStringBuilder, List<EnumDefinition> proxyParamEnums, String methodNameAppend) {
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
                 LOGGER.debug("added enum parameter to proxyParamEnums");
            }
        }
        
        String parameters = "";
        List<String> parameterStringList = new ArrayList<String>();
        for (Parameter parameter : parameterList) {
            LOGGER.debug("parameter type name is " + parameter.Type.Name);
            if (parameter.IsRequired) {
                String parameterString = getDefaultType(parameter) + " " + getDefaultTypeName(parameter);
                parameterStringList.add(parameterString);
            } else {
                String parameterString = getDefaultType(parameter) + " " + getDefaultTypeName(parameter);
                parameterStringList.add(parameterString);
            }
        }
        
        parameters = String.join(", ", parameterStringList);
        LOGGER.debug("parameters is " + parameters);
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
}
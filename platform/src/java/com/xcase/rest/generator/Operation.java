package com.xcase.rest.generator;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Operation
{
    public Operation(String returnType, String method, String path, List<Parameter> parameters, String operationId, String description, String proxyName) {
        this.Path = path;
        this.Method = method;
        this.Parameters = parameters;
        this.OperationId = operationId;
        this.Description = description;
        this.ReturnType = returnType;
        this.ProxyName = proxyName;
    }

    public String ProxyName;
    public String Path;
    public String Method;
    public List<Parameter> Parameters;
    public String OperationId;
    public String Description;
    public String ReturnType;

    public static String getCleanJavaReturnType(String returnType) {
        boolean isArray = false;
        if (returnType != null && !returnType.isEmpty()) {
            if (returnType.endsWith("[]")) {
                isArray = true;
                returnType = getCleanJavaReturnType(returnType.substring(0, returnType.length() - 2));
            }

            if (returnType.equals("object")) {
                return "Object";
            }
            else if (returnType.equals("string")) {
                return "String";
            }

            if (isArray) {
                return returnType + "[]";
            }

            return returnType;
        }

        return returnType;
    }
}
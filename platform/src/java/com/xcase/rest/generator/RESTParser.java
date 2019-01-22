package com.xcase.rest.generator;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RESTParser implements IParser {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public abstract ProxyDefinition parseDoc(String document, IAPIProxySettingsEndpoint endpoint) throws Exception;
    public abstract RESTServiceDefinition parseDocForRestServiceDefinition(String document, IAPIProxySettingsEndpoint endpoint, String objectsPackageName) throws Exception;

    public static String fixGenericName(String name)
    {
//        LOGGER.debug("starting fixGenericName()");
        if (name.contains("[") == false || name.contains("]") == false)
        {
            return name;
        }

        if (name.startsWith("Dictionary[") || name.startsWith("IDictionary["))
        {
            return name.replace("[", "<").replace("]", ">");
        }

        if (name.equals("string[]"))
        {
            name = "String[]";
        }
        
        return name;
    }

    public static String fixTypeName(String typeName) {
//        LOGGER.debug("starting fixTypeName()");
        if (typeName == null || typeName.isEmpty()) {
            return typeName;
        }

        if (typeName.equals("IterableOfstring")) {
            return "List<string>";
        }

        String fixedTypeName = typeName
            .replace(":", "_Colon_")
            .replace(",", "_Comma_")
            .replace("-", "_Dash_")
            .replace(".", "_Dot_")
            .replace("(", "_Left_")
            .replace("%", "_Percent_")
            .replace(")", "_Right_")
            .replace("/", "_Slash_")
            .replace(" ", "_Space_");
//        LOGGER.debug("fixedTypeName is " + fixedTypeName);
        fixedTypeName = RESTParser.fixGenericName(fixedTypeName);
//        LOGGER.debug("fixedTypeName is " + fixedTypeName);
        char firstCharacter = fixedTypeName.charAt(0);
        if (!Character.isLetter(firstCharacter)) {
            fixedTypeName = "_" + fixedTypeName;
        }

        return fixedTypeName;
    }

    public static String fixJavaTypeName(String input) {
//        LOGGER.debug("starting fixMethodName()");
        if (input == null || input.isEmpty()) {
            return input;
        }

        String output = input.replace(" ", "");
        output = RESTParser.fixGenericName(output);
        output = output.replace(".", "__");
        char firstCharacter = output.charAt(0);
        if (!Character.isLetter(firstCharacter)) {
            output = "_" + output;
        }

        if (output.equals("DateTime")) {
            output = "Date";
        }

        if (output.equals("string[]")) {
            output = "String[]";
        }

        if (output.endsWith("[][]")) {
            output.replace("[][]", "[]");
        }

        return output;
    }


    public static String fixMethodName(String methodName) {
//        LOGGER.debug("starting fixMethodName()");
        if (methodName == null || methodName.isEmpty()) {
            return methodName;
        }

        String fixedMethodName = methodName
            .replace(":", "_Colon_")
            .replace("-", "_Dash_")
            .replace(".", "_Dot_")
            .replace("(", "_Left_")
            .replace("%", "_Percent_")
            .replace(")", "_Right_")
            .replace(" ", "_Space_");
        fixedMethodName = RESTParser.fixGenericName(fixedMethodName);
        char firstCharacter = fixedMethodName.charAt(0);
        if (!Character.isLetter(firstCharacter)) {
            fixedMethodName = "_" + fixedMethodName;
        }

        return fixedMethodName;
    }
}

package com.xcase.rest.generator;

import java.util.Arrays;

/**
 *
 * @author martinpg
 */
public abstract class WritableDefinition {
    public int textPadding = 0;
    
    public String defaultReturnValue(String returnType) {
        if (returnType == "int") {
            return "-1";
        }

        return "null";
    }

    public String getDefaultType(Parameter parameter) {
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

    public String getDefaultTypeName(Parameter x) {
        if (x.Type.TypeName.endsWith("Data") || x.Type.TypeName.endsWith("Data[]")) {
            /* TODO: do we need to do this?
            return "body";
             */
        }
        
        return x.Type.getCleanJavaTypeName();
    }
    
    public String getElementType(Parameter parameter) {
        return trimArray(getDefaultType(parameter));
    }
    
    public boolean isIntrinsicType(String typeName) {
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
    
    public String reformat(String description) {
        if (description == null) {
            return "";
        } else {
            return description.replace("\n", " ").replace("\r", " ").replaceAll(" +", " ");
        }
    }
    
    public boolean toStringIsValid(Parameter parameter) {
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
    
    public String trimArray(String type) {
        if (type.endsWith("[]")) {
            return type.substring(0, type.length() - 2);
        }
        
        return type;
    }
    
    public void writeHeaders(StringBuilder stringBuilder, String packageString) {
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
    
    public void writeIfNotNullStatementClosing(StringBuilder stringBuilder, String parameterName, String typeName) {
        writeLine(stringBuilder, "}");
    }

    public void writeIfNotNullStatementOpening(StringBuilder stringBuilder, String parameterName, String typeName) {
        if (isIntrinsicType(typeName)) {
            writeLine(stringBuilder, "if (true) {");
        } else {
            writeLine(stringBuilder, "if (" + parameterName + " != null) {");
        }
    }
    
    public void writeImportLine(StringBuilder stringBuilder, String importString) {
        writeLine(stringBuilder, "import " + importString + ";");
    }
    
    public void writeLine(StringBuilder stringBuilder) {
        stringBuilder.append("\n");
    }

    public void writeLine(StringBuilder stringBuilder, String text) {
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
    
    public void writeLogger(StringBuilder stringBuilder) {
        writeLine(stringBuilder);
        writeLine(stringBuilder, "protected static final Logger LOGGER = LogManager.getLogger();");
        writeLine(stringBuilder);
    }

    public void writeLogger(StringBuilder stringBuilder, String className) {
        writeLine(stringBuilder);
        writeLine(stringBuilder, "protected static final Logger LOGGER = LogManager.getLogger(" + className + ".class);");
        writeLine(stringBuilder);
    }
}

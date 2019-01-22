package com.xcase.rest.generator;

public class EnumDefinition extends WritableDefinition {
    public String Name;
    public String[] Values;
    
    public EnumDefinition(String name, String[] values) {
        Name = name;
        Values = values;
    }
    
    public StringBuilder writeToStringBuilder(String proxy, String objectsPackageName) {
        StringBuilder enumStringBuilder = new StringBuilder();
        String proxyParamEnumName = RESTParser.fixTypeName(Name);
        writeHeaders(enumStringBuilder, objectsPackageName);
        writeLine(enumStringBuilder, "/*");
        writeLine(enumStringBuilder, "/// enum for " + proxy);
        writeLine(enumStringBuilder, "*/");
        writeLine(enumStringBuilder, "public enum " + proxyParamEnumName + " {");
        for (String value : Values) {
            writeLine(enumStringBuilder, RESTParser.fixTypeName(value) + "(\"" + value + "\"),");
        }

        writeLine(enumStringBuilder, ";");
        writeLine(enumStringBuilder);
        writeLine(enumStringBuilder, "private String description;");
        writeLine(enumStringBuilder, proxyParamEnumName + "(String description) {");
        writeLine(enumStringBuilder, "this.description = description;");
        writeLine(enumStringBuilder, "}");
        writeLine(enumStringBuilder, "}");
        writeLine(enumStringBuilder);
        return enumStringBuilder;
    }
}

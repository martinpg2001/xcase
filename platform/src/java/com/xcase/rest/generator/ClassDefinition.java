package com.xcase.rest.generator;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import static com.xcase.rest.generator.RESTProxyGenerator.writeHeaders;
import java.io.File;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClassDefinition extends WritableDefinition {
    public ClassDefinition() {
        this.Properties = new ArrayList<TypeDefinition>();
    }
    
    public ClassDefinition(String name) {
        this.Name = name;
        this.Properties = new ArrayList<TypeDefinition>();
    }

    public String Name;
    public List<TypeDefinition> Properties;
    public String Inherits;

    public String getCleanClassName() {
        return RESTParser.fixTypeName(this.Name);
    }
    
    public StringBuilder writeToStringBuilder(String objectsPackageName) {
        StringBuilder classStringBuilder = new StringBuilder();
        String objectsPath = objectsPackageName.replace(".", "/");
        List<EnumDefinition> modelEnums = new ArrayList<EnumDefinition>();
        String modelClassName = getCleanClassName();
        writeHeaders(classStringBuilder, objectsPackageName);
        String inheritsClass = "";
        if (Inherits != null) {
            inheritsClass = " extends " + Inherits;
        }
        
        writeLine(classStringBuilder, "public class " + modelClassName + inheritsClass + " {");
        for (TypeDefinition typeDefinition : Properties) {
            String typeName = typeDefinition.getCleanJavaTypename();
            if (typeName == null || typeName.equals("")) {
                typeName = "object";
            } else {
                typeName = typeDefinition.getCleanJavaTypename();
            }
            
            writeLine(classStringBuilder, "public " + typeName + " " + RESTParser.fixJavaTypeName(typeDefinition.Name) + ";");
            if (typeDefinition.EnumValues != null) {
//                LOGGER.debug("typeDefinition EnumValues is not null");
                modelEnums.add(new EnumDefinition(typeName, typeDefinition.EnumValues));
            }
        }
        
        for (EnumDefinition modelEnum : modelEnums) {
            writeLine(classStringBuilder, "public enum " + RESTParser.fixJavaTypeName(modelEnum.Name));
            writeLine(classStringBuilder, "{");
            for (String value : modelEnum.Values) {
                writeLine(classStringBuilder, RESTParser.fixTypeName(value) + "(\"" + value + "\"),");
            }
            
            writeLine(classStringBuilder, ";");
            writeLine(classStringBuilder);
            writeLine(classStringBuilder, "private String description;");
            writeLine(classStringBuilder, RESTParser.fixTypeName(modelEnum.Name) + "(String description) {");
            writeLine(classStringBuilder, "this.description = description;");
            writeLine(classStringBuilder, "}");
            writeLine(classStringBuilder, "}");
            writeLine(classStringBuilder);
        }
        
        writeLine(classStringBuilder, "}");
        writeLine(classStringBuilder);
        File modelClassFile = new File("src/java/" + objectsPath, modelClassName + ".java");
        try (PrintWriter printWriter = new PrintWriter(modelClassFile, "UTF-8")) {
            printWriter.write(classStringBuilder.toString());
        } catch (Exception e) {
        } finally {
        }
        
        return classStringBuilder;        
    }
}
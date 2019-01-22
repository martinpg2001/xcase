package com.xcase.rest.generator;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TypeDefinition
{
    public TypeDefinition(String typeName, String name, String[] enumValues, boolean isNullableType)
    {
        this.TypeName = typeName;
        this.Name = name;
        this.EnumValues = enumValues;
        this.IsNullableType = isNullableType;
    }

    public String Name;
    public String TypeName;
    public String[] EnumValues;
    public boolean IsNullableType;

    public String getCleanTypeName()
    {
        /* Replace hyphenated type names with camelCase names */
        while (Name.contains("-"))
        {
             StringBuilder nameStringBuilder = new StringBuilder(Name);
             int index = nameStringBuilder.indexOf("-");
             nameStringBuilder.deleteCharAt(index);
             Name = nameStringBuilder.toString();
        }

        String fixedName = RESTParser.fixTypeName(Name);
        return "@" + fixedName;
    }

    public String getCleanJavaTypeName()
    {
        /* Replace hyphenated type names with camelCase names */
        while (Name.contains("-"))
        {
             StringBuilder nameStringBuilder = new StringBuilder(Name);
             int index = nameStringBuilder.indexOf("-");
             nameStringBuilder.deleteCharAt(index);
             Name = nameStringBuilder.toString();
        }

        if (checkJavaKeyword(Name))
        {
            Name = "_" + Name;
        }

        String fixedName = RESTParser.fixTypeName(Name);
        return fixedName;
    }

    public boolean checkJavaKeyword(String name)
    {
        String[] keywords = new String[] { "abstract", "continue", "for", "new", "switch", "default", "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while" };
        if (name == null)
        {
            return false;
        }

        if (Arrays.asList(keywords).contains(name))
        {
            return true;
        }

        return false;
    }

    public String getCleanJavaTypename()
    {
        if (TypeName == "bool")
        {
            TypeName = "boolean";
        }

        if (TypeName == "DateTime")
        {
            TypeName = "Date";
        }

        if (TypeName == "object")
        {
            TypeName = "Object";
        }

        if (TypeName == "string")
        {
            TypeName = "String";
        }

        if (TypeName == "string[]")
        {
            TypeName = "String[]";
        }

        return TypeName.replace("$", "").replace(" ", "").replace(".", "__");
    }
}
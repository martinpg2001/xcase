package com.xcase.rest.generator;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import java.lang.invoke.MethodHandles;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Parameter
{
    public TypeDefinition Type;
    public ParameterIn ParameterIn;
    public boolean IsEnum;
    public boolean IsRequired;
    public String Description;
    public String CollectionFormat;

    public Parameter(TypeDefinition type, ParameterIn parameterIn, boolean isRequired, String description, String collectionFormat) {
        this.Type = type;
        this.ParameterIn = parameterIn;
        this.IsRequired = isRequired || this.Type.EnumValues != null;
        this.Description = description;
        this.CollectionFormat = collectionFormat;
    }
}

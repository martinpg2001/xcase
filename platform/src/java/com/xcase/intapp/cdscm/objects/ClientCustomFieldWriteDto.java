package com.xcase.intapp.cdscm.objects;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.commons.lang3.*;
import org.apache.http.*;
import org.apache.http.message.*;
import org.apache.logging.log4j.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.URLUtils;

public class ClientCustomFieldWriteDto
{
    public String customFieldId;
    public String name;
    public valueTypeValues valueType;
    public boolean active;
    public boolean readOnly;
    public enum valueTypeValues
    {
        STRING("STRING"),
        BOOLEAN("BOOLEAN"),
        DATE("DATE"),
        NUMBER("NUMBER"),
        ;
        private String description;
        valueTypeValues(String description) {
            this.description = description;
        }
    }

}



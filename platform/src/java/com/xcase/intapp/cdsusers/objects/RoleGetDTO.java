package com.xcase.intapp.cdsusers.objects;

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

public class RoleGetDTO
{
    public String key;
    public String name;
    public String description;
    public CapabilityGetDTO[] capabilities;
    public typeValues type;
    public boolean readonly;
    public String createdOn;
    public String modifiedOn;
    public enum typeValues
    {
        REGULAR("REGULAR"),
        DEFAULT("DEFAULT"),
        SYSTEM("SYSTEM"),
        ;
        private String description;
        typeValues(String description) {
            this.description = description;
        }
    }

}



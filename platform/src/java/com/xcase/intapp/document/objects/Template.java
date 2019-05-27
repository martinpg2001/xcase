package com.xcase.intapp.document.objects;

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

public class Template
{
    public String category;
    public String content;
    public String createdBy;
    public String createdOn;
    public Object files;
    public String id;
    public String modifiedBy;
    public String modifiedOn;
    public String name;
    public String type;
}



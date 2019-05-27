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

public class UpdatesApiMatterModel
{
    public String clientId;
    public String key;
    public String matterId;
    public String modifiedOn;
}



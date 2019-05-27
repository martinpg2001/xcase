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

public class UserPostDTO
{
    public String userId;
    public String email;
    public boolean enabled;
    public String name;
    public String personKey;
    public String timeZoneId;
    public String locale;
    public KeyRef[] roles;
    public boolean enableOtp;
    public boolean timekeeper;
    public String exchangeUsername;
    public String exchangeHost;
    public String[] emailAliases;
    public Object timeLinks;
}



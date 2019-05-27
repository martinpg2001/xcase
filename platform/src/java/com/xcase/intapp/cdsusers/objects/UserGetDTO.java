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

public class UserGetDTO
{
    public String key;
    public String userId;
    public String email;
    public boolean enabled;
    public String name;
    public String firstName;
    public String lastName;
    public String personKey;
    public String timeZoneId;
    public String locale;
    public LinkGetDTO[] roles;
    public boolean external;
    public userOriginValues userOrigin;
    public boolean readonly;
    public String userIdentityProviderId;
    public String providerAlias;
    public boolean enableOtp;
    public boolean timekeeper;
    public String exchangeUsername;
    public String exchangeHost;
    public String[] emailAliases;
    public Object timeLinks;
    public String lastLoginOn;
    public String createdOn;
    public String modifiedOn;
    public String endDate;
    public enum userOriginValues
    {
        REGULAR_Slash_SYSTEM("REGULAR/SYSTEM"),
        ;
        private String description;
        userOriginValues(String description) {
            this.description = description;
        }
    }

}



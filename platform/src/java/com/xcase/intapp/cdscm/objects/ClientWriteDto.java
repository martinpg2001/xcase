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

public class ClientWriteDto
{
    public String clientId;
    public String name;
    public String status;
    public String description;
    public String closedOn;
    public String dunsNumber;
    public RoundingDTO rounding;
    public String timeNote;
    public String billableStatus;
    public String industry;
    public ClientPersonDto[] clientPersons;
    public ClientExternalIdentifierDto[] externalIdentifiers;
    public String lcidDictionary;
    public String ebillinghubValidation;
    public Object timelinks;
    public String forceCodeId;
    public String openedOn;
    public ProductAppExampleData _pricingAppData;
    public ProductAppExampleData _experienceAppData;
    public ProductAppExampleData _timeAppData;
    public SecurityDto security;
}



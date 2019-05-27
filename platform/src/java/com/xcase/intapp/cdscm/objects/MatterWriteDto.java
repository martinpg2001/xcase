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

public class MatterWriteDto
{
    public String matterId;
    public String name;
    public String status;
    public String shortDescription;
    public String description;
    public String lastBillOn;
    public String lastTimeEntryOn;
    public String openedOn;
    public String closedOn;
    public String organizationUnitId;
    public String practiceArea;
    public String currencyIsoCode;
    public String office;
    public String department;
    public MatterPersonDto[] matterPersons;
    public MatterExternalIdentifierDto[] externalIdentifiers;
    public RoundingDTO rounding;
    public String timeNote;
    public String billableStatus;
    public String lcidDictionary;
    public String ebillinghubValidation;
    public String clientId;
    public Object timelinks;
    public String forceCodeId;
    public ProductAppExampleData _pricingAppData;
    public ProductAppExampleData _experienceAppData;
    public ProductAppExampleData _timeAppData;
    public SecurityDto security;
}



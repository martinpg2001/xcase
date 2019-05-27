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

public class PersonGetDTO
{
    public String key;
    public String personId;
    public String firstName;
    public String middleName;
    public String lastName;
    public String name;
    public LinkGetDTO[] titles;
    public LinkGetDTO defaultTitle;
    public String email;
    public String costPoolId;
    public AddressDTO[] addresses;
    public CommunicationDTO[] communications;
    public boolean employee;
    public LinkGetDTO department;
    public LinkGetDTO office;
    public LinkGetDTO[] practiceAreas;
    public LinkGetDTO defaultPracticeArea;
    public ExternalIdDTO[] externalIds;
    public String createdOn;
    public String modifiedOn;
}



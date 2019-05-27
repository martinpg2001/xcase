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

public class PersonPatchDTO
{
    public boolean departmentDirty;
    public boolean officeDirty;
    public String personId;
    public String firstName;
    public String middleName;
    public String lastName;
    public String name;
    public KeyRef[] titles;
    public String email;
    public String costPoolId;
    public AddressDTO[] addresses;
    public CommunicationDTO[] communications;
    public KeyRef department;
    public KeyRef office;
    public KeyRef[] practiceAreas;
    public ExternalIdDTO[] externalIds;
}



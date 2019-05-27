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

public class AddressDTO
{
    public typeValues type;
    public String country;
    public String state;
    public String city;
    public String streetAddress;
    public String postalCode;
    public enum typeValues
    {
        Home("Home"),
        Work("Work"),
        ;
        private String description;
        typeValues(String description) {
            this.description = description;
        }
    }

}



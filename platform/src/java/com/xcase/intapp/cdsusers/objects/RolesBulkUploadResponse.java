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

public class RolesBulkUploadResponse
{
    public int created;
    public CreatedInfo[] createdList;
    public ErrorInfo[] errorList;
    public int errors;
    public int updated;
    public UpdatedInfo[] updatedList;
    public int uploaded;
}



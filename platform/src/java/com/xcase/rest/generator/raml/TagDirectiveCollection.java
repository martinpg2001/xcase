package com.xcase.rest.generator.raml;

import com.xcase.rest.generator.*;
import com.xcase.rest.generator.raml.events.*;
import com.xcase.rest.generator.raml.tokens.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import java.lang.invoke.MethodHandles;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagDirectiveCollection extends HashMap<String, TagDirective>
{
    /// <summary>
    /// Initializes a new instance of the <see cref="TagDirectiveCollection"/> class.
    /// </summary>
    public TagDirectiveCollection()
    {
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="TagDirectiveCollection"/> class.
    /// </summary>
    /// <param name="tagDirectives">Initial content of the collection.</param>
    public TagDirectiveCollection(List<TagDirective> tagDirectives)
    {
        for (TagDirective tagDirective : tagDirectives)
        {
            put(tagDirective.Handle, tagDirective);
        }
    }

    /// <summary/>
    protected String GetKeyForItem(TagDirective item)
    {
        return item.Handle;
    }

    /// <summary>
    /// Gets a value indicating whether the collection contains a directive with the same handle
    /// </summary>
    public boolean contains(TagDirective directive) {
        return containsKey(directive.Handle);
    }
}

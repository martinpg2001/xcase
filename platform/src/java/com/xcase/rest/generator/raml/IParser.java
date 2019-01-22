package com.xcase.rest.generator.raml;

import com.xcase.rest.generator.raml.events.ParsingEvent;
import com.xcase.rest.generator.*;
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

/// <summary>
/// Represents a YAML stream paser.
/// </summary>
public interface IParser {
    /// <summary>
    /// Gets the current event. Returns null before the first call to <see cref="MoveNext" />,
    /// and also after <see cref="MoveNext" /> returns false.
    /// </summary>
    ParsingEvent getCurrent();

    /// <summary>
    /// Moves to the next event.
    /// </summary>
    /// <returns>Returns true if there are more events available, otherwise returns false.</returns>
    boolean moveNext();
    
    ParsingEvent peek();
}

package com.xcase.rest.generator.raml.tokens;

import com.xcase.rest.generator.*;
import com.xcase.rest.generator.raml.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.rest.generator.raml.EventType;
import com.xcase.rest.generator.raml.IParsingEventVisitor;
import com.xcase.rest.generator.raml.Mark;
import java.lang.invoke.MethodHandles;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagDirective extends Token {
    private String handle;
    private String prefix;

    /// <summary>
    /// Gets the handle.
    /// </summary>
    /// <value>The handle.</value>
    public String Handle;

    /// <summary>
    /// Gets the prefix.
    /// </summary>
    /// <value>The prefix.</value>
    public String Prefix;
    
    public static Pattern tagHandleValidator = Pattern.compile("^!([0-9A-Za-z_\\-]*!)?$");

    /// <summary>
    /// Initializes a new instance of the <see cref="TagDirective"/> class.
    /// </summary>
    /// <param name="handle">The handle.</param>
    /// <param name="prefix">The prefix.</param>
    public TagDirective(String handle, String prefix) throws Exception {
        this(handle, prefix, Mark.Empty, Mark.Empty);
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="TagDirective"/> class.
    /// </summary>
    /// <param name="handle">The handle.</param>
    /// <param name="prefix">The prefix.</param>
    /// <param name="start">The start position of the token.</param>
    /// <param name="end">The end position of the token.</param>
    public TagDirective(String handle, String prefix, Mark start, Mark end) throws Exception {
        super(start, end);
        if (RAMLParser.isNullOrEmpty(handle)) {
            throw new Exception("Tag handle must not be empty.");
        }

        if (!tagHandleValidator.matcher(handle).matches()) {
            throw new Exception("Tag handle must start and end with '!' and contain alphanumerical characters only.");
        }

        this.handle = handle;
        if (RAMLParser.isNullOrEmpty(prefix)) {
            throw new Exception("Tag prefix must not be empty.");
        }

        this.prefix = prefix;
    }

    /// <summary>
    /// Determines whether the specified System.Object is equal to the current System.Object.
    /// </summary>
    /// <param name="obj">The System.Object to compare with the current System.Object.</param>
    /// <returns>
    /// true if the specified System.Object is equal to the current System.Object; otherwise, false.
    /// </returns>
    public boolean equals(Object obj) {
        TagDirective other = (TagDirective) obj;
        return other != null && handle.equals(other.handle) && prefix.equals(other.prefix);
    }

    /// <summary>
    /// Serves as a hash function for a particular type.
    /// </summary>
    /// <returns>
    /// A hash code for the current <see cref="T:System.Object"/>.
    /// </returns>
    public int getHashCode() {
        return handle.hashCode() ^ prefix.hashCode();
    }

    /// <summary/>
    public String toString() {
        return handle + " => " + prefix;
    }
}

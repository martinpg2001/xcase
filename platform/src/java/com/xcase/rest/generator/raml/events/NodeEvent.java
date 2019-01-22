package com.xcase.rest.generator.raml.events;

import com.google.gson.*;
import com.xcase.rest.generator.*;
import com.xcase.rest.generator.raml.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
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

public abstract class NodeEvent extends ParsingEvent
{
    public static Pattern anchorValidator = Pattern.compile("^[0-9a-zA-Z_\\-]+$");

    /// <summary>
    /// Gets the anchor.
    /// </summary>
    /// <value></value>
    public String Anchor;

    /// <summary>
    /// Gets the tag.
    /// </summary>
    /// <value></value>
    public String Tag;

    /// <summary>
    /// Gets a value indicating whether this instance is canonical.
    /// </summary>
    /// <value></value>
    public boolean IsCanonical;

    /// <summary>
    /// Initializes a new instance of the <see cref="NodeEvent"/> class.
    /// </summary>
    /// <param name="anchor">The anchor.</param>
    /// <param name="tag">The tag.</param>
    /// <param name="start">The start position of the event.</param>
    /// <param name="end">The end position of the event.</param>
    protected NodeEvent(String anchor, String tag, Mark start, Mark end) throws Exception {
        super(start, end);
        if (anchor != null)
        {
            if (anchor.length() == 0)
            {
                throw new Exception("Anchor value must not be empty.");
            }

            Matcher matcher = anchorValidator.matcher(anchor);
            if (!matcher.matches())
            {
                throw new Exception("Anchor value must contain alphanumerical characters only.");
            }
        }

        if (tag != null && tag.length() == 0)
        {
            throw new Exception("Tag value must not be empty.");
        }

        this.Anchor = anchor;
        this.Tag = tag;
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="NodeEvent"/> class.
    /// </summary>
    public NodeEvent(String anchor, String tag) throws Exception {
        this(anchor, tag, Mark.Empty, Mark.Empty);
    }
}

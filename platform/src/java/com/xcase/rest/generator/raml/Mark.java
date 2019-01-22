package com.xcase.rest.generator.raml;

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

public class Mark
{
    /// <summary>
    /// Gets a <see cref="Mark"/> with empty values.
    /// </summary>
    public static Mark Empty = new Mark();

    /// <summary>
    /// Gets / sets the absolute offset in the file
    /// </summary>
    public int Index;

    /// <summary>
    /// Gets / sets the number of the line
    /// </summary>
    public int Line;

    /// <summary>
    /// Gets / sets the index of the column
    /// </summary>
    public int Column;

    public Mark()
    {
        Line = 1;
        Column = 1;
    }

    public Mark(int index, int line, int column) throws Exception
    {
        if (index < 0)
        {
            throw new Exception("Index must be greater than or equal to zero.");
        }
        if (line < 1)
        {
            throw new Exception("Line must be greater than or equal to 1.");
        }
        if (column < 1)
        {
            throw new Exception("Column must be greater than or equal to 1.");
        }

        Index = index;
        Line = line;
        Column = column;
    }

    /// <summary>
    /// Returns a <see cref="System.String"/> that represents this instance.
    /// </summary>
    /// <returns>
    /// A <see cref="System.String"/> that represents this instance.
    /// </returns>
    public String toString()
    {
        return "Line: " + Line + ", Col: " + Column + ", Idx: " + Index;
    }

    /// <summary />
    public boolean equals(Object obj)
    {
        return equals((Mark) obj);
    }

    /// <summary />
    public boolean equals(Mark other)
    {
        return other != null
            && Index == other.Index
            && Line == other.Line
            && Column == other.Column;
    }

    /// <summary />
    public int getHashCode()
    {
        return Objects.hash(Line, Column);
    }

    /// <summary />
    public int compareTo(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("obj");
        }
        
        return compareTo((Mark) obj);
    }

    /// <summary />
    public int compareTo(Mark other) throws Exception {
        if (other == null) {
            throw new Exception("other");
        }

        int cmp = Line == other.Line ? 0 : 1;
        if (cmp == 0)
        {
            cmp = Column == other.Column ? 0 : 1;
        }

        return cmp;
    }
}

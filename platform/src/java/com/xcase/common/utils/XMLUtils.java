/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.utils;

import java.io.*;
import java.lang.invoke.MethodHandles;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;

/**
 *
 * @author martin
 */
public class XMLUtils {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * converts a Document to a String.
     *
     * @param document input document
     * @return string
     */
    public static String ConvertDocumentoString(Document document) {
        String output = null;
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            output = writer.getBuffer().toString().replaceAll("\n|\r", "");
        } catch (Exception e) {
        }

        return output;
    }

    private XMLUtils() {
    }
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
public class IntegrateLogEntry implements LogEntry {

    @XmlElement(name = "version")
    public String version;

    @XmlElement(name = "size")
    public String size;

    @XmlElement(name = "totalEntries")
    public String totalEntries;

    public String getSize() {
        return this.size;
    }

    public String getVersion() {
        return this.version;
    }

    public String getTotalEntries() {
        return this.totalEntries;
    }
}

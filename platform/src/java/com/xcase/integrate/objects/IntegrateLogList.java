/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author martinpg
 */
@XmlRootElement(name = "log_list")
public class IntegrateLogList {

    @XmlElementWrapper(name = "log_entries")
    @XmlElement(name = "log_entry")
    private List<IntegrateBulkLogEntry> logEntryList;

    @XmlElement(name = "next_page_starts_at")
    private String next_page_starts_at;

    public List<IntegrateBulkLogEntry> getLogEntries() {
        return this.logEntryList;
    }

    public String getNextPageStartsAt() {
        return this.next_page_starts_at;
    }
}

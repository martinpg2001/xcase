/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusSummary {

    @XmlElement(name = "entry")
    public List<Entry> entryList;

    public List<Entry> getEntryList() {
        return this.entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }
}

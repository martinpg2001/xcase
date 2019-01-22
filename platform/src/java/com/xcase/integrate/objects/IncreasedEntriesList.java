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
public class IncreasedEntriesList {

    List<IncreasedEntries> increasedEntries;

    public List<IncreasedEntries> getIncreasedEntries() {
        return this.increasedEntries;
    }

    @XmlElement(name = "increased_entries")
    public void setIncreasedEntries(List<IncreasedEntries> increasedEntries) {
        this.increasedEntries = increasedEntries;
    }
}

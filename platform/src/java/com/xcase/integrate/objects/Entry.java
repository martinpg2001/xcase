/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Entry {

    @XmlElement(name = "key")
    public String key;

    @XmlElement(name = "value")
    public String value;
}

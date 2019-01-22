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
public class AgentActivity {

    @XmlElement(name = "datasource_name")
    public String datasource_name;

    @XmlElement(name = "instance_id")
    public String instance_id;

    @XmlElement(name = "status")
    public String status;
}

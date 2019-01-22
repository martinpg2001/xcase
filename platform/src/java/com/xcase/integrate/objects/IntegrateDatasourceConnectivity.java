/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "operation_status_message")
public class IntegrateDatasourceConnectivity {

    @XmlElement(name = "message")
    public String message;

    @XmlElement(name = "status")
    public String status;
}

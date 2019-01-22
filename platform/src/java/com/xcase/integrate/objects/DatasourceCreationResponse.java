/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "create_datasource_response")
public class DatasourceCreationResponse {

    @XmlElement(name = "id")
    public int id;

    @XmlElement(name = "message")
    public String message;    
}

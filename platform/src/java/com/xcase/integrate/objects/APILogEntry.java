/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
public class APILogEntry {

    @XmlAttribute(name = "duration")
    public String duration;

    @XmlAttribute(name = "http_method")
    public String http_method;

    @XmlAttribute(name = "path_url")
    public String path_url;

    @XmlAttribute(name = "remote_ip")
    public String remote_ip;

    @XmlAttribute(name = "request_time")
    public String request_time;

    @XmlAttribute(name = "response_code")
    public String response_code;

    @XmlAttribute(name = "rule_log_id")
    public String rule_log_id;

    @XmlAttribute(name = "username")
    public String username;

    @XmlElement(name = "request_body")
    public String request_body;

    @XmlElement(name = "error_msg")
    public String error_msg;
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "rule")
public class IntegrateRule {

    @XmlElement(name = "id")
    public int id;

    @XmlElement(name = "name")
    public String name;

    @XmlElement(name = "description")
    public String description;

    public Date time_created;

    public Date last_updated;

    @XmlElement(name = "owner")
    public String owner;

    public boolean is_incomplete;

    public boolean is_autogenerated;

    public boolean is_conversion_required;

    public boolean is_asynchronous;

    public boolean is_comment_logging_enabled;

    public boolean has_sensitive_data;

    public String activation_mode;

    public String priority;

    public boolean is_log_rule_details;

    public boolean is_override_rule_log;

    public IntegrateInput[] inputs;

    public IntegrateOutput[] outputs;

//    public String location_concatenated;
//    public String[] location;
    public IntegrateRule() {

    }
}

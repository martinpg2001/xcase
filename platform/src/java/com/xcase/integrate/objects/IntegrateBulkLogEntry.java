/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.Date;
import javax.xml.bind.annotation.*;

/**
 *
 * @author martin
 */
@XmlRootElement(name = "log_entry")
public class IntegrateBulkLogEntry {

    public int id;

    public Date eventTime;

    public String message;

    public String status;

    public Integer duration;

    public String ruleName;
}

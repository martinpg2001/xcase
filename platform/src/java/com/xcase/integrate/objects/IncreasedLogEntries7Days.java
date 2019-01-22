/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import javax.xml.bind.annotation.*;

/**
 *
 * @author martinpg
 */
@XmlRootElement(name = "increased_log_entries_7_days")
public class IncreasedLogEntries7Days {

    @XmlElement(name = "api_log")
    public APIIncreasedEntriesList api_log;

    @XmlElement(name = "event_log")
    public EventIncreasedEntriesList event_log;

    @XmlElement(name = "rule_log")
    public RuleIncreasedEntriesList rule_log;

    @XmlElement(name = "security_log")
    public SecurityIncreasedEntriesList security_log;

    @XmlElement(name = "systems_log")
    public SystemsIncreasedEntriesList systems_log;

    @XmlElement(name = "webservice_log")
    public WebServiceIncreasedEntriesList webservice_log;
}

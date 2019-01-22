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
public class RuleLogEntry {

    @XmlAttribute(name = "cancel")
    public String cancel;

    @XmlAttribute(name = "details")
    public String details;

    @XmlAttribute(name = "duration")
    public String duration;

    @XmlElement(name = "view")
    public RuleLogEntryView view;

    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    public List<RuleLogEntryEvent> events;

    @XmlElementWrapper(name = "nodes")
    @XmlElement(name = "node")
    public List<RuleLogEntryNode> nodes;

    @XmlElementWrapper(name = "transitions")
    @XmlElement(name = "transition")
    public List<RuleLogEntryTransition> transitions;
}

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
@XmlRootElement(name = "agent_activities")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplianceAgentActivities {

    @XmlElement(name = "agent_activity")
    public List<AgentActivity> agentActivityList;

    public List<AgentActivity> getAgentActivityList() {
        return this.agentActivityList;
    }

    public void setAgentActivityList(List<AgentActivity> agentActivityList) {
        this.agentActivityList = agentActivityList;
    }
}

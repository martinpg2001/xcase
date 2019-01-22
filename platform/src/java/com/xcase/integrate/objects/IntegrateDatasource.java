/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.objects;

import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author martinpg
 */
@XmlRootElement(name = "datasource")
public class IntegrateDatasource {
    @XmlElement(name = "action_timeout")
    public int action_timeout;
    
    public String baseURL;
    
    public String credentials_type;
    
    public IntegrateDatasourceType datasource_type;
    
    public String entry_point;
    
    //public String general_type;

    public String host;
        
    @XmlElement(name = "id")
    public int id;
    
    public Date lastUpdated;
    
    public IntegrateDatasourceLocation location;

    @XmlElement(name = "name")
    public String name;
    
    @XmlElement(name = "network_timeout")
    public int network_timeout;
    
    public Object options;    

    public IntegrateDatasourceOwner owner;
    
    public String password;

    public int port;
    
    public String privateKey;
    
    @XmlElement(name = "requires_sync_group")
    public boolean requires_sync_group;
    
    public int retries;

    public boolean ssl;
    
    public Date timeCreated;
    
    public String userAgent;
    
    public String user_name;

    public boolean virtual;
    
    public String getGeneralType() {
        if (datasource_type != null) {
            return datasource_type.general_type;
        }
        
        return null;
    }
    
    public String getOwner() {
        if (owner != null) {
            return owner.name;
        }
        
        return null;
    }
}

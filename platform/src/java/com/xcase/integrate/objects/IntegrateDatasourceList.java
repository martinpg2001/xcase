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
@XmlRootElement(name = "datasources")
public class IntegrateDatasourceList {

    List<IntegrateDatasource> datasources;

    public List<IntegrateDatasource> getDatasources() {
        return this.datasources;
    }

    @XmlElement(name = "datasource")
    public void setDatasources(List<IntegrateDatasource> datasources) {
        this.datasources = datasources;
    }
}

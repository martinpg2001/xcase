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
@XmlRootElement(name = "outputs")
public class IntegrateOutputList {

    List<IntegrateOutput> outputs;

    public List<IntegrateOutput> getOutputs() {
        return this.outputs;
    }

    @XmlElement(name = "output")
    public void setOutputs(List<IntegrateOutput> outputs) {
        this.outputs = outputs;
    }
}

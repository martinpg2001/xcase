/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.utils;

import java.lang.invoke.MethodHandles;
import java.net.InetAddress;
import java.util.StringTokenizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martinpg
 */
public class MachineUtils {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method takes name as machine name before '.'.
     */
    public static String getMachineName(String sysName) {
//        LOGGER.debug("starting getMachineName()");
        String firstString;
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(sysName, ".");
            firstString = stringTokenizer.nextToken();
            return firstString;
        } catch (Exception e) {
            LOGGER.warn("could not get machineName: " + e.getMessage());
        }

        return "";
    }

    public static String getUpToFirstTenCharactersOfMachineName() {
        return getUpToFirstCharactersOfMachineName(10);
    }

    public static String getUpToFirstCharactersOfMachineName(int n) {
        String machineName = "";
        try {
            machineName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            LOGGER.warn("failed to retrieve machine name");
        }

        String uniqueMachineName = getMachineName(machineName);
        String uniqueUpToFirstTenCharactersOfMachineName = uniqueMachineName.substring(0, Math.min(uniqueMachineName.length(), n));
        LOGGER.debug("uniqueUpToFirstTenCharactersOfMachineName is " + uniqueUpToFirstTenCharactersOfMachineName);
        return uniqueUpToFirstTenCharactersOfMachineName;
    }

    private MachineUtils() {
    }
}

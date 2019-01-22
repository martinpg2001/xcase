/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.utils;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martinpg
 */
public class TimeUtils {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method returns the number of seconds between minutes.
     */
    public static int convertSecondsToTinyHand(int seconds) {
        return seconds % 60;
    }

    /**
     * This method returns the number of minutes that are equivalent to the
     * input number of seconds. Any remaining seconds are discarded.
     */
    public static int convertSecondsToLittleHand(int seconds) {
        int minutesInSeconds = seconds - (convertSecondsToTinyHand(seconds));
        int minutes = minutesInSeconds / 60;
        return minutes;
    }

    /**
     * This method returns the number of hours that are equivalent to the input
     * number of seconds. Any remaining seconds are discarded.
     */
    public static int convertSecondsToBigHand(int seconds) {
        int hoursInSeconds = seconds - (seconds % 3600);
        int hours = hoursInSeconds / 3600;
        return hours;
    }

    private TimeUtils() {
    }
}

/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.impl.simple.core;

import java.lang.invoke.*;
import java.util.*;
import org.apache.commons.collections4.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CompareHelper {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static CompareResult compareStringLists(List<String> sourceStringList, List<String> targetStringList) {
        if (sourceStringList == null || targetStringList == null) {
            LOGGER.warn("One of the lists is null");
            return new CompareResult(false, "One of the lists is null");
        }

        java.util.Collection subtractTargetFromSourceCollection = CollectionUtils.subtract(sourceStringList, targetStringList);
        java.util.Collection subtractSourceFromTargetCollection = CollectionUtils.subtract(targetStringList, sourceStringList);
        if (subtractTargetFromSourceCollection.isEmpty() && subtractSourceFromTargetCollection.isEmpty()) {
            return new CompareResult("Lists match");
        } else {
            String subtractTargetFromSourceCollectionString = subtractTargetFromSourceCollection.toString();
            String subtractSourceFromTargetCollectionString = subtractSourceFromTargetCollection.toString();
            String compareStringListsMessage = "Source strings not in target:" + subtractTargetFromSourceCollectionString + "; target strings not in source: " + subtractSourceFromTargetCollectionString;
            return new CompareResult(false, compareStringListsMessage);
        }
    }

    public static CompareResult compareHashMaps(HashMap<?, ?> sourceHashMap, HashMap<?, ?> targetHashMap, ArrayList<String> ignoreKeysArrayList) {
        if (sourceHashMap == null || targetHashMap == null) {
            LOGGER.warn("one of the hashmaps is null");
            return new CompareResult(false, "One of the hashmaps is null");
        }

        LOGGER.debug("neither of the hashmaps is null");
        if (ignoreKeysArrayList == null) {
            ignoreKeysArrayList = new ArrayList<String>();
        }
        
        boolean compareHashMapsBoolean = true;
        StringBuilder compareHashMapsStringBuilder = new StringBuilder();
        try {
            for (Object sourceKey : sourceHashMap.keySet()) {
                LOGGER.debug("checking sourceKey " + sourceKey);
                if (sourceHashMap.get(sourceKey) == null) {
                    if (targetHashMap.get(sourceKey) != null) {
                        LOGGER.warn("for sourceKey " + sourceKey + ": source value is null, but target value is not null");
                        compareHashMapsBoolean = false;
                    }
                } else if (!ignoreKeysArrayList.contains((String) sourceKey) && !sourceHashMap.get(sourceKey).equals(targetHashMap.get(sourceKey))) {
                    LOGGER.warn("values do not match for sourceKey " + sourceKey);
                    compareHashMapsBoolean = false;
                    compareHashMapsStringBuilder.append("values do not match for sourceKey " + sourceKey + ": sourceValue " + sourceHashMap.get(sourceKey) + " and targetValue " + targetHashMap.get(sourceKey) + ";");
                }
            }

            for (Object targetKey : targetHashMap.keySet()) {
                LOGGER.debug("checking targetKey " + targetKey);
                if (targetHashMap.get(targetKey) == null) {
                    if (sourceHashMap.get(targetKey) != null) {
                        LOGGER.warn("for targetKey " + targetKey + ": target value is null, but source value is not null");
                        compareHashMapsBoolean = false;
                    }
                } else if (!ignoreKeysArrayList.contains((String) targetKey) && !targetHashMap.get(targetKey).equals(sourceHashMap.get(targetKey))) {
                    LOGGER.warn("values do not match for targetKey " + targetKey);
                    compareHashMapsBoolean = false;
                    compareHashMapsStringBuilder.append("values do not match for targetKey " + targetKey + ": sourceValue " + sourceHashMap.get(targetKey) + " and targetValue " + targetHashMap.get(targetKey) + ";");
                }
            }
        } catch (NullPointerException npe) {
            return new CompareResult(false, "Exception comparing hash maps: " + npe.getMessage());
        }

        if (compareHashMapsBoolean) {
            return new CompareResult("HashMaps match");
        } else {
            return new CompareResult(false, "HashMaps do not match: " + compareHashMapsStringBuilder.toString());
        }
    }
    
    public static CompareResult compareHashMaps(HashMap<?, ?> sourceHashMap, HashMap<?, ?> targetHashMap) {
        return compareHashMaps(sourceHashMap, targetHashMap, new ArrayList<String>());
    }

    private CompareHelper() {
    }
}

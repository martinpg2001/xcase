package com.xcase.azure.impl.simple.methods;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.monitor.ActivityLogs;
import com.microsoft.azure.management.monitor.implementation.ActivityLogsInner;
import com.microsoft.azure.management.monitor.implementation.EventDataInner;
import com.xcase.azure.factories.AzureResponseFactory;
import com.xcase.azure.transputs.GetEventsRequest;
import com.xcase.azure.transputs.GetEventsResponse;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetEventsMethod extends BaseAzureMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetEventsResponse getEvents(GetEventsRequest request) {
        LOGGER.debug("starting getEvents()");
        GetEventsResponse response = AzureResponseFactory.createGetEventsResponse();
        LOGGER.debug("created response");
        try {
            Azure azure = request.getAzure();
            String filter = request.getFilter();
            String select = request.getSelect();
            ActivityLogs activityLogs = azure.activityLogs();
            LOGGER.debug("activityLogs event catgeories list size is " + activityLogs.listEventCategories().size());
            ActivityLogsInner activityLogsInner = activityLogs.inner();
            LOGGER.debug("got activityLogsInner");
            PagedList<EventDataInner> eventInnerPageList = activityLogsInner
                    .list(filter, select);
            LOGGER.debug("got paginated list of events");
            Iterator<EventDataInner> eventInnerIterator = eventInnerPageList.iterator();
            while (eventInnerIterator.hasNext()) {
                LOGGER.debug("next eventInner is "
                        + ((EventDataInner) eventInnerIterator.next()).eventName().localizedValue());
            }

        } catch (Exception e) {
            LOGGER.warn("exception getting events: " + e.getMessage());
        }

        return response;
    }

}

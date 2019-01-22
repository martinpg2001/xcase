/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice;

import com.xcase.webservice.objects.WebServiceException;
import com.xcase.webservice.transputs.InvokeWebServiceRequest;
import com.xcase.webservice.transputs.InvokeWebServiceResponse;
import java.io.IOException;

/**
 *
 * @author martin
 */
public interface WebServiceExternalAPI {

    /**
     * This method copies a file publicly shared by someone to a user's mybox.
     * 'file_id' and 'public_name' params identify a publicly shared file, you
     * should provide either file_id or public name (like '31nhke0ahp') as a
     * parameter. 'folder_id' is the id of a user's folder, where files are to
     * be copied.
     *
     * On a successful result, the status will be 'addtomybox_ok'. If the result
     * wasn't successful, the status field can be: 'addtomybox_error',
     * 'not_logged_id', 'application_restricted', 's_link_exists'.
     *
     * @param invokeWebServiceRequest request object
     * @return response object
     * @throws IOException IO exception
     */
    InvokeWebServiceResponse invokeWebService(InvokeWebServiceRequest invokeWebServiceRequest) throws IOException, WebServiceException;
}

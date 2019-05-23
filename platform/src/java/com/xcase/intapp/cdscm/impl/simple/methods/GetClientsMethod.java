package com.xcase.intapp.cdscm.impl.simple.methods;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.intapp.cdscm.factories.CDSCMResponseFactory;
import com.xcase.intapp.cdscm.transputs.GetClientsRequest;
import com.xcase.intapp.cdscm.transputs.GetClientsResponse;
import com.xcase.intapp.cdscm.transputs.GetClientsRequest;
import com.xcase.intapp.cdscm.transputs.GetClientsResponse;

public class GetClientsMethod extends BaseCDSCMMethod {
	/**
	 * log4j object.
	 */
	protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public GetClientsResponse getClients(GetClientsRequest request) {
		LOGGER.debug("starting getClients()");
		GetClientsResponse response = CDSCMResponseFactory.createGetClientsResponse();
		LOGGER.debug("created response");
		try {
			String baseVersionUrl = getAPIVersionUrl();
			LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
			endPoint = baseVersionUrl + request.getOperationPath() + "?";
            int limit = request.getLimit();
            if (limit > 0) {
                endPoint = endPoint + "&limit=" + limit;
            }
            
            int skip = request.getSkip();
            if (skip > 0) {
                endPoint = endPoint + "&skip=" + skip;
            }
            
			LOGGER.debug("endPoint is " + endPoint);
			String accessToken = request.getAccessToken();
			LOGGER.debug("accessToken is " + accessToken);
			Header authorizationHeader = createCDSCMAuthenticationTokenHeader(accessToken);
			LOGGER.debug("created Authorization header");
			Header acceptHeader = createAcceptHeader();
			Header contentTypeHeader = createContentTypeHeader();
			Header authenticationToken = new BasicHeader("IntegrateAuthenticationToken", accessToken);
			Header[] headers = { acceptHeader, authenticationToken, authorizationHeader, contentTypeHeader };
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, parameters, null);
			int responseCode = commonHttpResponse.getResponseCode();
			LOGGER.debug("responseCode is " + responseCode);
			if (responseCode == request.getSuccessResponseCode()) {
				handleExpectedResponseCode(response, commonHttpResponse);
			} else {
				handleUnexpectedResponseCode(response, commonHttpResponse);
			}
		} catch (Exception e) {
			handleUnexpectedException(response, e);
		}

		return response;
	}

}

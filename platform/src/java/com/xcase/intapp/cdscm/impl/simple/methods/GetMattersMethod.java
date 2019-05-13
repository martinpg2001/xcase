package com.xcase.intapp.cdscm.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.intapp.cdscm.factories.CDSCMResponseFactory;
import com.xcase.intapp.cdscm.transputs.GetMattersRequest;
import com.xcase.intapp.cdscm.transputs.GetMattersResponse;
import java.lang.invoke.MethodHandles;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetMattersMethod extends BaseCDSCMMethod {
	/**
	 * log4j object.
	 */
	protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public GetMattersResponse getMatters(GetMattersRequest request) {
		LOGGER.debug("starting getMatters()");
		GetMattersResponse response = CDSCMResponseFactory.createGetMattersResponse();
		LOGGER.debug("created response");
		try {
			String baseVersionUrl = getAPIVersionUrl();
			LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
			String clientId = request.getClientId();
			LOGGER.debug("clientId is " + clientId);
			endPoint = baseVersionUrl + request.getOperationPath().replace("{clientId}", clientId);
			LOGGER.debug("endPoint is " + endPoint);
			String accessToken = request.getAccessToken();
			LOGGER.debug("accessToken is " + accessToken);
			Header authorizationHeader = createCDSCMAuthenticationTokenHeader(accessToken);
			LOGGER.debug("created Authorization header");
			Header acceptHeader = createAcceptHeader();
			Header contentTypeHeader = createContentTypeHeader();
			Header authenticationToken = new BasicHeader("IntegrateAuthenticationToken", accessToken);
			Header[] headers = { acceptHeader, authenticationToken, authorizationHeader, contentTypeHeader };
			CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
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

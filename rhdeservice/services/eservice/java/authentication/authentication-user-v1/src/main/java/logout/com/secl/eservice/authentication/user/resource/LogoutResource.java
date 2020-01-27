package com.secl.eservice.authentication.user.resource;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.secl.eservice.authentication.user.request.LogoutRequest;
import com.secl.eservice.authentication.user.response.LogoutResponse;
import com.secl.eservice.authentication.user.response.LogoutResponseBody;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("authenticationUserV1LogoutResource")
@RequestMapping(Api.AUTHENTICATION_USER_V1_RESOURCE)
public class LogoutResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	private ConsumerTokenServices tokenServices;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PreAuthorize("securityHasPermission('"+Api.AUTHENTICATION_USER_V1_RESOURCE+Api.AUTHENTICATION_USER_V1_LOGOUT_PATH+"')")
	@PostMapping(path = Api.AUTHENTICATION_USER_V1_LOGOUT_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody LogoutResponse onPost(HttpServletRequest req, @Valid @RequestBody LogoutRequest request) throws AppException {
		String url = Api.AUTHENTICATION_USER_V1_RESOURCE.concat(Api.AUTHENTICATION_USER_V1_LOGOUT_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		LogoutResponse response = getResponse(request, url);
		String authorization = req.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")) {
            String tokenId = authorization.substring("Bearer".length() + 1);
            tokenServices.revokeToken(tokenId);
            response.getBody().setData(true);
        }
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private LogoutResponse getResponse(LogoutRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		LogoutResponseBody body = LogoutResponseBody.builder().build();
		return LogoutResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public LogoutResponse fallbackGetAllOnPost(LogoutRequest request) {
		String url = Api.AUTHENTICATION_USER_V1_RESOURCE.concat(Api.AUTHENTICATION_USER_V1_LOGOUT_PATH);
		LogoutResponse response = getResponse(request, url);
		return response;
	}

}

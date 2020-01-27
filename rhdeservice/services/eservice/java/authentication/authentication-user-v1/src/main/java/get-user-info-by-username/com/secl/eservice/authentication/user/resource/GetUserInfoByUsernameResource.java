package com.secl.eservice.authentication.user.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.secl.eservice.authentication.user.request.GetUserInfoByUsernameRequest;
import com.secl.eservice.authentication.user.response.GetUserInfoByUsernameResponse;
import com.secl.eservice.authentication.user.response.GetUserInfoByUsernameResponseBody;
import com.secl.eservice.authentication.user.service.GetUserInfoByUsernameService;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("authenticationUserV1GetListByUsernameResource")
@RequestMapping(Api.AUTHENTICATION_USER_V1_RESOURCE)
public class GetUserInfoByUsernameResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("authenticationUserV1GetListByUsernameService")
	private GetUserInfoByUsernameService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PreAuthorize("securityHasPermission('"+Api.AUTHENTICATION_USER_V1_RESOURCE+Api.AUTHENTICATION_USER_V1_GET_USER_INFO_BY_USERNAME_PATH+"')")
	@PostMapping(path = Api.AUTHENTICATION_USER_V1_GET_USER_INFO_BY_USERNAME_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody GetUserInfoByUsernameResponse onPost(@Valid @RequestBody GetUserInfoByUsernameRequest request) throws AppException {
		String url = Api.AUTHENTICATION_USER_V1_RESOURCE.concat(Api.AUTHENTICATION_USER_V1_GET_USER_INFO_BY_USERNAME_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		GetUserInfoByUsernameResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private GetUserInfoByUsernameResponse getResponse(GetUserInfoByUsernameRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		GetUserInfoByUsernameResponseBody body = GetUserInfoByUsernameResponseBody.builder().build();
		return GetUserInfoByUsernameResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public GetUserInfoByUsernameResponse fallbackGetAllOnPost(GetUserInfoByUsernameRequest request) {
		String url = Api.AUTHENTICATION_USER_V1_RESOURCE.concat(Api.AUTHENTICATION_USER_V1_GET_USER_INFO_BY_USERNAME_PATH);
		GetUserInfoByUsernameResponse response = getResponse(request, url);
		return response;
	}

}

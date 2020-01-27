package com.secl.eservice.authentication.registration.resource;

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

import com.secl.eservice.authentication.registration.request.RegistrationApprovedRequest;
import com.secl.eservice.authentication.registration.response.RegistrationApprovedResponse;
import com.secl.eservice.authentication.registration.response.RegistrationApprovedResponseBody;
import com.secl.eservice.authentication.registration.service.RegistrationApprovedService;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("authenticationRegistrationV1RegistrationApprovedResource")
@RequestMapping(Api.AUTHENTICATION_REGISTRATION_V1_RESOURCE)
public class RegistrationApprovedResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("authenticationRegistrationV1RegistrationApprovedService")
	private RegistrationApprovedService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PreAuthorize("securityHasPermission('"+Api.AUTHENTICATION_REGISTRATION_V1_RESOURCE+Api.AUTHENTICATION_REGISTRATION_V1_REGISTRATION_APPROVED_PATH+"')")
	@PostMapping(path = Api.AUTHENTICATION_REGISTRATION_V1_REGISTRATION_APPROVED_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody RegistrationApprovedResponse onPost(@Valid @RequestBody RegistrationApprovedRequest request) throws AppException {
		String url = Api.AUTHENTICATION_REGISTRATION_V1_RESOURCE.concat(Api.AUTHENTICATION_REGISTRATION_V1_REGISTRATION_APPROVED_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RegistrationApprovedResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private RegistrationApprovedResponse getResponse(RegistrationApprovedRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		RegistrationApprovedResponseBody body = RegistrationApprovedResponseBody.builder().build();
		return RegistrationApprovedResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public RegistrationApprovedResponse fallbackGetAllOnPost(RegistrationApprovedRequest request) {
		String url = Api.AUTHENTICATION_REGISTRATION_V1_RESOURCE.concat(Api.AUTHENTICATION_REGISTRATION_V1_REGISTRATION_APPROVED_PATH);
		RegistrationApprovedResponse response = getResponse(request, url);
		return response;
	}

}

package com.secl.eservice.authentication.registration.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.secl.eservice.authentication.registration.request.SaveRequest;
import com.secl.eservice.authentication.registration.response.SaveResponse;
import com.secl.eservice.authentication.registration.response.SaveResponseBody;
import com.secl.eservice.authentication.registration.service.SaveService;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("authenticationRegistrationV1SaveResource")
@RequestMapping(Api.PUBLIC_AUTHENTICATION_REGISTRATION_V1_RESOURCE)
public class SaveResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("authenticationRegistrationV1SaveService")
	private SaveService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	//@PreAuthorize("securityHasPermission('"+Api.AUTHENTICATION_REGISTRATION_V1_RESOURCE+Api.AUTHENTICATION_REGISTRATION_V1_SAVE_PATH+"')")
	@PostMapping(path = Api.AUTHENTICATION_REGISTRATION_V1_SAVE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody SaveResponse onPost(@Valid @RequestBody SaveRequest request) throws AppException {
		String url = Api.PUBLIC_AUTHENTICATION_REGISTRATION_V1_RESOURCE.concat(Api.AUTHENTICATION_REGISTRATION_V1_SAVE_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		//System.out.println(request.getBody().getRegistration().getNIdNo());
//		AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SaveResponse response = getResponse(request, url);
		response = service.perform(null, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private SaveResponse getResponse(SaveRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		SaveResponseBody body = SaveResponseBody.builder().build();
		return SaveResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public SaveResponse fallbackGetAllOnPost(SaveRequest request) {
		String url = Api.PUBLIC_AUTHENTICATION_REGISTRATION_V1_RESOURCE.concat(Api.AUTHENTICATION_REGISTRATION_V1_SAVE_PATH);
		SaveResponse response = getResponse(request, url);
		return response;
	}

}

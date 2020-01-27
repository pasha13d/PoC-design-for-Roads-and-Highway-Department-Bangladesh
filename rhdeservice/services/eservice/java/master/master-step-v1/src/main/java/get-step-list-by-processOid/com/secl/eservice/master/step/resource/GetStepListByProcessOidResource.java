package com.secl.eservice.master.step.resource;


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

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.master.step.request.GetStepListByProcessOidRequest;
import com.secl.eservice.master.step.response.GetStepListByProcessOidResponse;
import com.secl.eservice.master.step.response.GetStepListByProcessOidResponseBody;
import com.secl.eservice.master.step.service.GetStepListByProcessOidService;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("masterStepV1GetStepListByProcessOidResource")
@RequestMapping(Api.MASTER_STEP_V1_RESOURCE)
public class GetStepListByProcessOidResource {

	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;

	@Autowired
	@Qualifier("masterStepV1GetStepListByProcessOidService")
	private GetStepListByProcessOidService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PreAuthorize("securityHasPermission('"+Api.MASTER_STEP_V1_RESOURCE+ Api.MASTER_STEP_V1_GET_STEP_LIST_BY_PROCESSOID_PATH+"')")
	@PostMapping(path = Api.MASTER_STEP_V1_GET_STEP_LIST_BY_PROCESSOID_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody GetStepListByProcessOidResponse onPost(@RequestBody GetStepListByProcessOidRequest request) throws AppException {
		String url = Api.MASTER_STEP_V1_RESOURCE.concat(Api.MASTER_STEP_V1_GET_STEP_LIST_BY_PROCESSOID_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		GetStepListByProcessOidResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private GetStepListByProcessOidResponse getResponse(GetStepListByProcessOidRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		GetStepListByProcessOidResponseBody body = GetStepListByProcessOidResponseBody.builder().build();
		return GetStepListByProcessOidResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public GetStepListByProcessOidResponse fallbackGetAllOnPost(GetStepListByProcessOidRequest request) {
		String url = Api.MASTER_STEP_V1_RESOURCE.concat(Api.MASTER_STEP_V1_GET_STEP_LIST_BY_PROCESSOID_PATH);
		GetStepListByProcessOidResponse response = getResponse(request, url);
		return response;
	}
}

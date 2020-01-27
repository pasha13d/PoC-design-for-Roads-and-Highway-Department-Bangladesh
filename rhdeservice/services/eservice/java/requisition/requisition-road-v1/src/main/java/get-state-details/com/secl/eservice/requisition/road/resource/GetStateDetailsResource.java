package com.secl.eservice.requisition.road.resource;


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
import com.secl.eservice.requisition.road.request.GetStateDetailsRequest;
import com.secl.eservice.requisition.road.response.GetStateDetailsResponse;
import com.secl.eservice.requisition.road.response.GetStateDetailsResponseBody;
import com.secl.eservice.requisition.road.service.GetStateDetailsService;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("requisitionRoadV1GetStateDetailsResource")
@RequestMapping(Api.REQUISITION_ROAD_V1_RESOURCE)
public class GetStateDetailsResource {

	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;

	@Autowired
	@Qualifier("requisitionRoadV1GetStateDetailsService")
	private GetStateDetailsService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PreAuthorize("securityHasPermission('"+Api.REQUISITION_ROAD_V1_RESOURCE+ Api.REQUISITION_ROAD_V1_GET_STATE_DETAILS_PATH+"')")
	@PostMapping(path = Api.REQUISITION_ROAD_V1_GET_STATE_DETAILS_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody GetStateDetailsResponse onPost(@RequestBody GetStateDetailsRequest request) throws AppException {
		String url = Api.REQUISITION_ROAD_V1_RESOURCE.concat(Api.REQUISITION_ROAD_V1_GET_STATE_DETAILS_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		GetStateDetailsResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private GetStateDetailsResponse getResponse(GetStateDetailsRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		GetStateDetailsResponseBody body = GetStateDetailsResponseBody.builder().build();
		return GetStateDetailsResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public GetStateDetailsResponse fallbackGetAllOnPost(GetStateDetailsRequest request) {
		String url = Api.REQUISITION_ROAD_V1_RESOURCE.concat(Api.REQUISITION_ROAD_V1_GET_STATE_DETAILS_PATH);
		GetStateDetailsResponse response = getResponse(request, url);
		return response;
	}
}

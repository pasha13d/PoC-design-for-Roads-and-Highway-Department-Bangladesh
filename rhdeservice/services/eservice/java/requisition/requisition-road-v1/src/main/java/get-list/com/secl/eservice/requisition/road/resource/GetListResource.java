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
import com.secl.eservice.requisition.road.request.GetListRequest;
import com.secl.eservice.requisition.road.response.GetListResponse;
import com.secl.eservice.requisition.road.response.GetListResponseBody;
import com.secl.eservice.requisition.road.service.GetListService;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("requisitionRoadV1GetListResource")
@RequestMapping(Api.REQUISITION_ROAD_V1_RESOURCE)
public class GetListResource {

	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;

	@Autowired
	@Qualifier("requisitionRoadV1GetListService")
	private GetListService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PreAuthorize("securityHasPermission('"+Api.REQUISITION_ROAD_V1_RESOURCE+ Api.REQUISITION_ROAD_V1_GET_LIST_PATH+"')")
	@PostMapping(path = Api.REQUISITION_ROAD_V1_GET_LIST_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody GetListResponse onPost(@RequestBody GetListRequest request) throws AppException {
		String url = Api.REQUISITION_ROAD_V1_RESOURCE.concat(Api.REQUISITION_ROAD_V1_GET_LIST_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		GetListResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private GetListResponse getResponse(GetListRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		GetListResponseBody body = GetListResponseBody.builder().build();
		return GetListResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public GetListResponse fallbackGetAllOnPost(GetListRequest request) {
		String url = Api.REQUISITION_ROAD_V1_RESOURCE.concat(Api.REQUISITION_ROAD_V1_GET_LIST_PATH);
		GetListResponse response = getResponse(request, url);
		return response;
	}
}

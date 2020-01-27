package com.secl.eservice.master.document.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.secl.eservice.master.document.request.UploadRequest;
import com.secl.eservice.master.document.request.UploadRequestBody;
import com.secl.eservice.master.document.response.UploadResponse;
import com.secl.eservice.master.document.response.UploadResponseBody;
import com.secl.eservice.master.document.service.UploadService;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.request.ServiceRequestHeader;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("masterDocumentV1UploadResource")
@RequestMapping(Api.MASTER_DOCUMENT_V1_RESOURCE)
public class UploadResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("masterDocumentV1UploadService")
	private UploadService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PreAuthorize("securityHasPermission('"+Api.MASTER_DOCUMENT_V1_RESOURCE+Api.MASTER_DOCUMENT_V1_UPLOAD_PATH+"')")
	@PostMapping(path = Api.MASTER_DOCUMENT_V1_UPLOAD_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public @ResponseBody UploadResponse onPost(@RequestParam("file") MultipartFile file, @RequestParam("oid") String oid) throws AppException {
		
		ServiceRequestHeader header = ServiceRequestHeader.builder()
				.requestId(UUID.randomUUID().toString())
				.requestTime(new DateTime())
				.build();
			
			Map<String, Object> meta = new HashMap<String, Object>();
//			UploadRequestBody body = UploadRequestBody.builder().file(file).oid(oid).build();
			UploadRequestBody body = UploadRequestBody.builder().file(file).build();
			UploadRequest request = UploadRequest.builder().header(header).meta(meta).body(body).build();
		
		String url = Api.MASTER_DOCUMENT_V1_RESOURCE.concat(Api.MASTER_DOCUMENT_V1_UPLOAD_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UploadResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private UploadResponse getResponse(UploadRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		UploadResponseBody body = UploadResponseBody.builder().build();
		return UploadResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public UploadResponse fallbackGetAllOnPost(UploadRequest request) {
		String url = Api.MASTER_DOCUMENT_V1_RESOURCE.concat(Api.MASTER_DOCUMENT_V1_UPLOAD_PATH);
		UploadResponse response = getResponse(request, url);
		return response;
	}

}

package com.secl.eservice.master.document.resource;


import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.master.document.request.DownloadRequest;
import com.secl.eservice.master.document.response.DownloadResponse;
import com.secl.eservice.master.document.response.DownloadResponseBody;
import com.secl.eservice.master.document.service.DownloadService;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@Slf4j
@RestController("masterDocumentV1DownloadResource")
@RequestMapping(Api.MASTER_DOCUMENT_V1_RESOURCE)
public class DownloadResource {

	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("masterDocumentV1DonwloadService")
	private DownloadService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PreAuthorize("securityHasPermission('"+Api.MASTER_DOCUMENT_V1_RESOURCE+ Api.MASTER_DOCUMENT_V1_DOWNLOAD_PATH+"')")
	@PostMapping(path = Api.MASTER_DOCUMENT_V1_DOWNLOAD_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> onPost(HttpServletRequest req, @Valid @RequestBody DownloadRequest request) throws AppException {
		String url = Api.MASTER_DOCUMENT_V1_RESOURCE.concat(Api.MASTER_DOCUMENT_V1_DOWNLOAD_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		DownloadResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		
		if(StringUtils.isBlank(response.getBody().getFilePath())) {
			throw new AppException(request.getHeader(), Code.C_500.get(), "Report file not found");
		}
		
		String filePath = env.getProperty("file.upload-dir") + "/" + response.getBody().getFilePath(); 
        Path path = Paths.get(filePath);
		String contentType = null;
		UrlResource resource = null;
        try {
            resource = new UrlResource(path.toUri());
            contentType = req.getServletContext().getMimeType(filePath);
        } catch (Exception e) {
            log.error("An exception occurred while get report : ", e);
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
        }
        
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
 
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .contentType(MediaType.parseMediaType(contentType))
            .body(resource);
	}

	private DownloadResponse getResponse(DownloadRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		DownloadResponseBody body = DownloadResponseBody.builder().build();
		return DownloadResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public DownloadResponse fallbackGetAllOnPost(DownloadRequest request) {
		String url = Api.MASTER_DOCUMENT_V1_RESOURCE.concat(Api.MASTER_DOCUMENT_V1_DOWNLOAD_PATH);
		DownloadResponse response = getResponse(request, url);
		return response;
	}
}

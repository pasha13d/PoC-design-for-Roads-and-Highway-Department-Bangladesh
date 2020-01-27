package com.secl.eservice.master.email.resource;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

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
import com.secl.eservice.master.email.request.SendEmailRequest;
import com.secl.eservice.master.email.response.SendEmailResponse;
import com.secl.eservice.master.email.response.SendEmailResponseBody;
import com.secl.eservice.master.email.service.SendEmailService;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("masterEmailV1SendEmailResource")
@RequestMapping(Api.MASTER_EMAIL_V1_RESOURCE)
public class SendEmailResource {

	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;

	@Autowired
	@Qualifier("masterEmailV1SendEmailService")
	private SendEmailService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PreAuthorize("securityHasPermission('"+Api.MASTER_EMAIL_V1_RESOURCE+ Api.MASTER_EMAIL_V1_SEND_EMAIL_PATH+"')")
	@PostMapping(path = Api.MASTER_EMAIL_V1_SEND_EMAIL_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody SendEmailResponse onPost(@RequestBody SendEmailRequest request) throws AppException, AddressException, MessagingException, IOException {
		String url = Api.MASTER_EMAIL_V1_RESOURCE.concat(Api.MASTER_EMAIL_V1_SEND_EMAIL_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SendEmailResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private SendEmailResponse getResponse(SendEmailRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		SendEmailResponseBody body = SendEmailResponseBody.builder().build();
		return SendEmailResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public SendEmailResponse fallbackGetAllOnPost(SendEmailRequest request) {
		String url = Api.MASTER_EMAIL_V1_RESOURCE.concat(Api.MASTER_EMAIL_V1_SEND_EMAIL_PATH);
		SendEmailResponse response = getResponse(request, url);
		return response;
	}
}

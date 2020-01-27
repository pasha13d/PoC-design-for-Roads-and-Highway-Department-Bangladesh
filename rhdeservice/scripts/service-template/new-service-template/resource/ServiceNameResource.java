package <ServiceNameResourcePackage>.resource;

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

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import <ServiceNameResourcePackage>.request.<ServiceName>Request;
import <ServiceNameResourcePackage>.response.<ServiceName>Response;
import <ServiceNameResourcePackage>.response.<ServiceName>ResponseBody;
import <ServiceNameResourcePackage>.service.<ServiceName>Service;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.HeaderUtil;
import com.secl.eservice.util.constant.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("<SERVICE_PATH_CC>Resource")
@RequestMapping(Api.<SERVICE_RESOURCE_PATH_UC>_RESOURCE)
public class <ServiceName>Resource {

	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;

	@Autowired
	@Qualifier("<SERVICE_PATH_CC>Service")
	private <ServiceName>Service service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PreAuthorize("securityHasPermission('"+Api.<SERVICE_RESOURCE_PATH_UC>_RESOURCE+ Api.<SERVICE_NAME_PATH_UC>_PATH+"')")
	@PostMapping(path = Api.<SERVICE_NAME_PATH_UC>_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody <ServiceName>Response onPost(@RequestBody <ServiceName>Request request) throws AppException {
		String url = Api.<SERVICE_RESOURCE_PATH_UC>_RESOURCE.concat(Api.<SERVICE_NAME_PATH_UC>_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		<ServiceName>Response response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private <ServiceName>Response getResponse(<ServiceName>Request request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		<ServiceName>ResponseBody body = <ServiceName>ResponseBody.builder().build();
		return <ServiceName>Response.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public <ServiceName>Response fallbackGetAllOnPost(<ServiceName>Request request) {
		String url = Api.<SERVICE_RESOURCE_PATH_UC>_RESOURCE.concat(Api.<SERVICE_NAME_PATH_UC>_PATH);
		<ServiceName>Response response = getResponse(request, url);
		return response;
	}
}

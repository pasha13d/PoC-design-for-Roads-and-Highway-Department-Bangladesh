package com.secl.eservice.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.annotations.Expose;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.constant.Code;
import com.secl.eservice.util.constant.Constant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {

	private @Expose ServiceResponseHeader header;
	private @Expose Map<String, Object> meta;
	private @Expose Map<String, Object> body;

	public ExceptionResponse(AppException ex) {
		this.header = getResponseHeader(ex);
		this.meta = new HashMap<String, Object>();
		this.body = new HashMap<String, Object>();
	}

	public ExceptionResponse(Exception ex) {
		this.header = getResponseHeader2(ex);
		this.meta = new HashMap<String, Object>();
		this.body = new HashMap<String, Object>();
	}
	
	private ServiceResponseHeader getResponseHeader(AppException ex) {
		return ServiceResponseHeader.builder()
			.responseCode(ex.getCode())
			.responseMessage(ex.getMessage())
			.requestId(ex.getHeader().getRequestId())
			.responseVersion(ex.getHeader().getRequestVersion())
			.requestSourceService(ex.getHeader().getRequestSourceService())
			.hopCount(ex.getHeader().getHopCount() == null ? 1 : ex.getHeader().getHopCount() + 1)
			.traceId(StringUtils.isBlank(ex.getHeader().getTraceId()) ? UUID.randomUUID().toString() : ex.getHeader().getTraceId())
			.build();
	}
	
	private ServiceResponseHeader getResponseHeader2(Exception ex) {
		return ServiceResponseHeader.builder()
			.responseCode(Code.C_500.get())
			.responseMessage(ex.getMessage())
			.build();
	}
	
	@Override
	public String toString() {
		return Constant.print(this);
	}

}
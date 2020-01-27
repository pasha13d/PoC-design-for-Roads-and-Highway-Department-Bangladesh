package com.secl.eservice.handler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.secl.eservice.request.ServiceRequestHeader;
import com.secl.eservice.util.constant.Constant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@SuppressWarnings("serial")
public class AppException extends Exception {
	
	private @Expose String code;
	private @Expose String message;
	private @Expose ServiceRequestHeader header;

	public AppException(ServiceRequestHeader header, String message) {
		super(message);
		this.message = message;
		this.header = header;
	}

	public AppException(ServiceRequestHeader header, String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
		this.header = header;
	}

	@Override
	public String toString() {
		return Constant.print(this);
	}
}
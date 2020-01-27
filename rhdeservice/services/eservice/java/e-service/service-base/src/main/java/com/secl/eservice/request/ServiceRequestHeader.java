package com.secl.eservice.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.secl.eservice.util.constant.Constant;
import com.secl.eservice.util.constant.Message;
import com.secl.eservice.util.date.DateTimeDeserializer;
import com.secl.eservice.util.date.DateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestHeader {

	@NotNull(message=Message.REQUEST_MSG_PREFIX+"header_null_request_id")
	@NotBlank(message=Message.REQUEST_MSG_PREFIX+"header_empty_request_id")
	private @Expose String requestId; 
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"header_null_request_source")
	@NotBlank(message=Message.REQUEST_MSG_PREFIX+"header_empty_request_source")
	private @Expose String requestSource;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"header_null_request_source_service")
	@NotBlank(message=Message.REQUEST_MSG_PREFIX+"header_empty_request_source_service")
	private @Expose String requestSourceService;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"header_null_request_client")
	@NotBlank(message=Message.REQUEST_MSG_PREFIX+"header_empty_request_client")
	private @Expose String requestClient;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"header_null_request_type")
	@NotBlank(message=Message.REQUEST_MSG_PREFIX+"header_empty_request_type")
	private @Expose String requestType;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"header_null_request_time")
	@JsonSerialize(using=DateTimeSerializer.class)
	@JsonDeserialize(using=DateTimeDeserializer.class)
	private @Expose DateTime requestTime;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"header_null_request_version")
	@NotBlank(message=Message.REQUEST_MSG_PREFIX+"header_empty_request_version")
	private @Expose String requestVersion;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"header_null_request_timeout_in_seconds")
	private @Expose Integer requestTimeoutInSeconds;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"header_null_request_retry_count")
	@Min(value=0, message=Message.REQUEST_MSG_PREFIX+"header_invalid_request_retry_count")
	private @Expose Integer requestRetryCount;
	
	@Min(value=0, message=Message.REQUEST_MSG_PREFIX+"header_invalid_request_hop_count")
	private @Expose Integer hopCount;
	
	private @Expose String traceId;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}


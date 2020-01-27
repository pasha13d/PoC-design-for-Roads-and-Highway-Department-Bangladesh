package com.secl.eservice.master.document.request;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

import com.secl.eservice.request.ServiceRequestHeader;
import com.secl.eservice.util.constant.Constant;
import com.secl.eservice.util.constant.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
public class DownloadRequest {

	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_header")
	private @Expose ServiceRequestHeader header;

	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_meta")
	private @Expose Map<String, Object> meta;

	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_body")
	private @Expose DownloadRequestBody body;

	@Override
	public String toString() {
		return Constant.print(this);
	}
}

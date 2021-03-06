package com.secl.eservice.authentication.registration.response;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.secl.eservice.response.ServiceResponseHeader;
import com.secl.eservice.util.constant.Constant;

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
public class SaveResponse {

	private @Expose ServiceResponseHeader header;
	private @Expose Map<String, Object> meta;
	private @Expose SaveResponseBody body;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}

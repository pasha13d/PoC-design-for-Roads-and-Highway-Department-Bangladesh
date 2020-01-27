package com.secl.eservice.authentication.user.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
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
public class LogoutRequestBody {

	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_operation")
	private @Expose String operation;

	@Override
	public String toString() {
		return Constant.print(this);
	}

}

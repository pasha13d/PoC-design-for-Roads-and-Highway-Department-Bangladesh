package com.secl.eservice.authentication.registration.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.secl.eservice.authentication.registration.model.RegistrationReceived;
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
public class RegistrationReceivedRequestBody {

	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_registration")	

	private @Expose RegistrationReceived registration;

	@Override
	public String toString() {
		return Constant.print(this);
	}

}

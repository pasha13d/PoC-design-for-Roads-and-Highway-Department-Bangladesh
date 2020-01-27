package com.secl.eservice.master.email.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.secl.eservice.master.email.response.SendEmail;
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
public class SendEmailRequestBody {

	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_operation")
	@NotBlank(message=Message.REQUEST_MSG_PREFIX+"empty_operation")
	private @Expose SendEmail sendMail;

	@Override
	public String toString() {
		return Constant.print(this);
	}

}

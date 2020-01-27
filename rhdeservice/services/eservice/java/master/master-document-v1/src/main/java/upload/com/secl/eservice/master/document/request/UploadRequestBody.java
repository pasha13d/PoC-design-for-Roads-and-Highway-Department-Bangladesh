package com.secl.eservice.master.document.request;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.secl.eservice.util.constant.Constant;
import com.secl.eservice.util.constant.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@Builder
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor

public class UploadRequestBody {

	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_file")
	private MultipartFile file;
	
//	@Valid
//	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_oid")
//	private String oid;
	@Override
	public String toString() {
		return Constant.print(this);
	}

}

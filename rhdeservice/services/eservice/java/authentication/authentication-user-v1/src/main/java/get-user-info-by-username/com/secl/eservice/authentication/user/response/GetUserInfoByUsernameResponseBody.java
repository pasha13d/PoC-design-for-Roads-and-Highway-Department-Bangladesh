package com.secl.eservice.authentication.user.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.secl.eservice.authentication.user.model.GetUserInfoByUsernameRole;
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
public class GetUserInfoByUsernameResponseBody {

	private @Expose GetUserInfoByUsernameRole data;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}


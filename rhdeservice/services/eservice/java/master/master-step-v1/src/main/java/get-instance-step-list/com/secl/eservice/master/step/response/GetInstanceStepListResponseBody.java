package com.secl.eservice.master.step.response;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

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
public class GetInstanceStepListResponseBody {

	private @Expose List<GetInstanceStepList> data;

	@Override
	public String toString() {
		return Constant.print(this);
	}
}

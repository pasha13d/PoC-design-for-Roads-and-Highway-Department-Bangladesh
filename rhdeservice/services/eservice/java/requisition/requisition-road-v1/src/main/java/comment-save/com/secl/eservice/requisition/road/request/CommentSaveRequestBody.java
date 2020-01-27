package com.secl.eservice.requisition.road.request;

import javax.validation.Valid;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.secl.eservice.requisition.road.model.CommentSave;
import com.secl.eservice.util.constant.Constant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
public class CommentSaveRequestBody {

	@Valid
	private @Expose CommentSave comment;	

	@Override
	public String toString() {
		return Constant.print(this);
	}

}

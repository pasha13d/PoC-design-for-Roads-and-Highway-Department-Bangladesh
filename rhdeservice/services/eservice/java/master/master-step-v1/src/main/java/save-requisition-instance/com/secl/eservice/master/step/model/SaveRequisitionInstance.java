package com.secl.eservice.master.step.model;

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
@NoArgsConstructor
@AllArgsConstructor
public class SaveRequisitionInstance {

	private @Expose String roadrequisitionoid, stepoid, isdone, isactive;
		
    @Override
    public String toString() {
    	return Constant.print(this);
    }
}

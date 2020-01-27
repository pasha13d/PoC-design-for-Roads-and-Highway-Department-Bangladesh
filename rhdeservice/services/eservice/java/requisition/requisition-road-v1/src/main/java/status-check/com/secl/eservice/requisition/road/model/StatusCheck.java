package com.secl.eservice.requisition.road.model;

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
public class StatusCheck {

	private @Expose String oid, nextStep, currentStepOid, step,fromstepOid, fileName, actualFilename ;
		
    @Override
    public String toString() {
    	return Constant.print(this);
    }
}

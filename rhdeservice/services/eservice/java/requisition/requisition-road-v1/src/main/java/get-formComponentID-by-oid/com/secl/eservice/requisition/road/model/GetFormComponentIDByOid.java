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
public class GetFormComponentIDByOid {

	private @Expose String formComponentId, oid, forward, backward, approve, nextStepOid, previousStepOid;

    @Override
    public String toString() {
    	return Constant.print(this);
    }
}

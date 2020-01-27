package com.secl.eservice.requisition.road.response;

import com.google.gson.annotations.Expose;

import com.secl.eservice.util.constant.Constant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetStateDetails {

    private @Expose String to_step, from_step, fileName, actualFilename, createdby, createdon;

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

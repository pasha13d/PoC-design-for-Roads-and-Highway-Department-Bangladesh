package com.secl.eservice.requisition.road.response;

import com.google.gson.annotations.Expose;

import com.secl.eservice.util.constant.Constant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommentList {

    private @Expose String oid, roadrequisitionoid, comment, stepoid, createdby, createdon;

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

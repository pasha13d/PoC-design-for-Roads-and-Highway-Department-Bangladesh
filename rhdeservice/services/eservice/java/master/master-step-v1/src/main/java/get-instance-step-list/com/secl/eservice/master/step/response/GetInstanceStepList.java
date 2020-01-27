package com.secl.eservice.master.step.response;

import com.google.gson.annotations.Expose;

import com.secl.eservice.util.constant.Constant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetInstanceStepList {

    private @Expose String text, isdone, isactive;

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

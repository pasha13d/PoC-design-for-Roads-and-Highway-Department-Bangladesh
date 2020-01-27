package com.secl.eservice.master.email.response;

import com.google.gson.annotations.Expose;

import com.secl.eservice.util.constant.Constant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmail {

    private @Expose String toAddress, subject, body;

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

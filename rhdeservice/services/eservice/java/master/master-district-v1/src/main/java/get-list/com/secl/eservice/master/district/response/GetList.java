package com.secl.eservice.master.district.response;

import com.google.gson.annotations.Expose;

import com.secl.eservice.util.constant.Constant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetList {

    private @Expose String oid, districtNameEn, districtNameBn;

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

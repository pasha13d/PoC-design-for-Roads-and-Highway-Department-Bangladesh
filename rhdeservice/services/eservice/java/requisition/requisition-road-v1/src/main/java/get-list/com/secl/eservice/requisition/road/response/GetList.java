package com.secl.eservice.requisition.road.response;

import com.google.gson.annotations.Expose;

import com.secl.eservice.util.constant.Constant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetList {

    private @Expose String oid, division, district, upazilla, postalcode, location, startpoint, endpoint, isriverorwaterbodynear, purpose, status, nextstep;

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

package com.secl.eservice.authentication.user.model;

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
public class GetUserInfoByUsernameRole {

	private @Expose String oid, username, nameEn, nameBn, roleId, resetRequired;

    @Override
    public String toString() {
    	return Constant.print(this);
    }
}

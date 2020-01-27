package com.secl.eservice.authentication.user.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;
import com.secl.eservice.authentication.user.request.GetUserInfoByUsernameRequest;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class GetUserInfoByUsernameQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getUserInfoSql(AuthUser user, GetUserInfoByUsernameRequest request){
		
		List<Object> param = Lists.newArrayList(user.getUsername());
		
		String sql = "select l.oid, l.nameEn, l.nameBn, l.resetRequired, r.roleId, l.username"
				+ " from " + Table.LOGIN + " l,"
				+ Table.ROLE + " r"
				+ " where 1 = 1"
				+ " and l.roleOid = r.oid"
				+ " and l.username = ?";
		
		 Object[] data = param.toArray(new Object[param.size()]);
	     return new ImmutablePair<String, Object[]>(sql, data);
	}
	

}

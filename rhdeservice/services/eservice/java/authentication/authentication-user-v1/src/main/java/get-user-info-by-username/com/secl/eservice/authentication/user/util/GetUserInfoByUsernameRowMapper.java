package com.secl.eservice.authentication.user.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.authentication.user.model.GetUserInfoByUsernameRole;

public class GetUserInfoByUsernameRowMapper implements RowMapper<GetUserInfoByUsernameRole> {
	
	public GetUserInfoByUsernameRole mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetUserInfoByUsernameRole entity = GetUserInfoByUsernameRole.builder()
		.oid(rs.getString("oid"))
		.nameEn(rs.getString("nameen"))
		.nameBn(rs.getString("namebn"))
		.roleId(rs.getString("roleid"))
		.username(rs.getString("username"))
		.resetRequired(rs.getString("resetrequired"))
		.build();
		return entity;
	}
	
}

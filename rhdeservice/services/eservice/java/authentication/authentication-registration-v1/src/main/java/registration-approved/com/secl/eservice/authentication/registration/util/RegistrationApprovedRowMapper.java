package com.secl.eservice.authentication.registration.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.authentication.registration.model.RegistrationApproved;

public class RegistrationApprovedRowMapper implements RowMapper<RegistrationApproved> {
	
	public RegistrationApproved mapRow(ResultSet rs, int rowNum) throws SQLException {
		RegistrationApproved entity = RegistrationApproved.builder()
		.oid(rs.getString("oid"))
		.status(rs.getString("status"))
		.comment(rs.getString("comment"))
		
		.build();
		return entity;
	}
	
}

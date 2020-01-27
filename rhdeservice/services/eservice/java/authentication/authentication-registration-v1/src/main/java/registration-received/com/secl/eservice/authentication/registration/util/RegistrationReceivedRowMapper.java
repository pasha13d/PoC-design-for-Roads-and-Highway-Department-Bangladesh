package com.secl.eservice.authentication.registration.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.authentication.registration.model.RegistrationReceived;

public class RegistrationReceivedRowMapper implements RowMapper<RegistrationReceived> {
	
	public RegistrationReceived mapRow(ResultSet rs, int rowNum) throws SQLException {
		RegistrationReceived entity = RegistrationReceived.builder()
		.oid(rs.getString("oid"))
		.status(rs.getString("status"))
		.comment(rs.getString("comment"))
		.receivedBy(rs.getString("receivedBy"))
		
		.build();
		return entity;
	}
	
}

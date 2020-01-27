package com.secl.eservice.requisition.road.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.requisition.road.model.StatusCheck;

public class StatusCheckRowMapper implements RowMapper<StatusCheck> {
	
	public StatusCheck mapRow(ResultSet rs, int rowNum) throws SQLException {
		StatusCheck entity = StatusCheck.builder()
		.oid(rs.getString("oid"))
		.nextStep(rs.getString("nextStep"))
		
		.build();
		return entity;
	}
	
}

package com.secl.eservice.requisition.road.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.requisition.road.model.GetPreviousStepData;;

public class StepRowMapper implements RowMapper<GetPreviousStepData> {
	
	public GetPreviousStepData mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetPreviousStepData entity = GetPreviousStepData.builder()
		.oid(rs.getString("oid"))
		.build();
		return entity;
	}
	
}

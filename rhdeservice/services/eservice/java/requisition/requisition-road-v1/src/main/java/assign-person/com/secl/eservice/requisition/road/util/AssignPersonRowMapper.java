package com.secl.eservice.requisition.road.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.requisition.road.model.AssignPerson;

public class AssignPersonRowMapper implements RowMapper<AssignPerson> {
	
	public AssignPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
		AssignPerson entity = AssignPerson.builder()
		.oid(rs.getString("oid"))
		.nextStep(rs.getString("nextStep"))
		
		.build();
		return entity;
	}
	
}

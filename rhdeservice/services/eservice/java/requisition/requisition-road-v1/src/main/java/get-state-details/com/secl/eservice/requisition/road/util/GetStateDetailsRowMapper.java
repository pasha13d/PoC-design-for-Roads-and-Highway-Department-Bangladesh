package com.secl.eservice.requisition.road.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.requisition.road.response.GetStateDetails;

public class GetStateDetailsRowMapper implements RowMapper<GetStateDetails> {

	public GetStateDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetStateDetails entity = new GetStateDetails();
		entity.setFrom_step(rs.getString("from_step"));	
		entity.setTo_step(rs.getString("to_step"));	
		entity.setFileName(rs.getString("fileName")); 
		entity.setActualFilename(rs.getString("actualFilename")); 	
		entity.setCreatedby(rs.getString("createdby"));	
		entity.setCreatedon(rs.getString("createdon"));	
		return entity;
	}
}

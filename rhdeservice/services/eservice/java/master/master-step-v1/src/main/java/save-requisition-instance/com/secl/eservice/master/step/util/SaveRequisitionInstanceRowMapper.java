package com.secl.eservice.master.step.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.master.step.model.SaveRequisitionInstance;

public class SaveRequisitionInstanceRowMapper implements RowMapper<SaveRequisitionInstance> {
	
	public SaveRequisitionInstance mapRow(ResultSet rs, int rowNum) throws SQLException {
		SaveRequisitionInstance entity = SaveRequisitionInstance.builder()
		.roadrequisitionoid(rs.getString("roadrequisitionoid"))
		.stepoid(rs.getString("stepoid"))
		.isdone(rs.getString("isdone"))
		.isactive(rs.getString("isactive"))
		.build();
		return entity;
	}
	
}

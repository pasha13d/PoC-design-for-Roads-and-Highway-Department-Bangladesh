package com.secl.eservice.master.step.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.master.step.response.GetLoginStepMapping;

public class GetLoginStepMappingRowMapper implements RowMapper<GetLoginStepMapping> {

	public GetLoginStepMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetLoginStepMapping entity = new GetLoginStepMapping();
		entity.setLoginid(rs.getString("loginid"));	
		entity.setStepoid(rs.getString("stepoid"));
		entity.setStepName(rs.getString("stepName"));
		entity.setName(rs.getString("name"));
		entity.setSortOrder(rs.getString("sortOrder"));
		return entity;
	}
}

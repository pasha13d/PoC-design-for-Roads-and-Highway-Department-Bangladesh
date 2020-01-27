package com.secl.eservice.master.step.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.master.step.response.GetInstanceStepList;

public class GetInstanceStepListRowMapper implements RowMapper<GetInstanceStepList> {

	public GetInstanceStepList mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetInstanceStepList entity = new GetInstanceStepList();
		entity.setText(rs.getString("text"));
		entity.setIsdone(rs.getString("isdone"));
		entity.setIsactive(rs.getString("isactive"));
		return entity;
	}
}

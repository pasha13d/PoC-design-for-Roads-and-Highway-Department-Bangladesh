package com.secl.eservice.master.step.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.master.step.response.GetStepListByProcessOid;

public class GetStepListByProcessOidRowMapper implements RowMapper<GetStepListByProcessOid> {

	public GetStepListByProcessOid mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetStepListByProcessOid entity = new GetStepListByProcessOid();
		entity.setOid(rs.getString("oid"));
		return entity;
	}
}

package com.secl.eservice.requisition.road.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.requisition.road.model.GetFormComponentIDByOid;

public class GetFormComponentIDByOidRowMapper implements RowMapper<GetFormComponentIDByOid> {
	
	public GetFormComponentIDByOid mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetFormComponentIDByOid entity = GetFormComponentIDByOid.builder()
				.oid(rs.getString("oid"))
				.formComponentId(rs.getString("formComponentId"))
				.forward(rs.getString("forward"))
				.backward(rs.getString("backward"))
				.approve(rs.getString("approve"))
				.nextStepOid(rs.getString("nextstepoid"))
				.previousStepOid(rs.getString("previousstepoid"))
				.build();
		return entity;
	}
	
}

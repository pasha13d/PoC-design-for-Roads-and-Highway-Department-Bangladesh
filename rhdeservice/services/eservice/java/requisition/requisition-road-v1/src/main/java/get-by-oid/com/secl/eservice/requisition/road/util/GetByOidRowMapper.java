package com.secl.eservice.requisition.road.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.requisition.road.model.GetByOid;

public class GetByOidRowMapper implements RowMapper<GetByOid> {
	
	public GetByOid mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOid entity = GetByOid.builder()
		.oid(rs.getString("oid"))
		.division(rs.getString("division"))
		.district(rs.getString("district"))
		.upazilla(rs.getString("upazilla"))
		.postalCode(rs.getString("postalCode"))
		.location(rs.getString("location"))
		.startPoint(rs.getString("startPoint"))
		.endPoint(rs.getString("endPoint"))
		.purpose(rs.getString("purpose"))
		.isriverOrWaterbodynear(rs.getString("isriverOrWaterbodynear"))
		.status(rs.getString("status"))
		.nextStep(rs.getString("nextStep"))
		.build();
		return entity;
	}
	
}

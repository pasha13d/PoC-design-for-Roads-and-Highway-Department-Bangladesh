package com.secl.eservice.requisition.road.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.requisition.road.response.GetList;

public class GetListRowMapper implements RowMapper<GetList> {

	public GetList mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetList entity = new GetList();
		entity.setOid(rs.getString("oid"));
		entity.setDivision(rs.getString("division"));	
		entity.setDistrict(rs.getString("district"));	
		entity.setUpazilla(rs.getString("upazilla"));	
		entity.setPostalcode(rs.getString("postalcode"));
		entity.setLocation(rs.getString("location"));	
		entity.setStartpoint(rs.getString("startpoint"));	
		entity.setEndpoint(rs.getString("endpoint"));	
		entity.setIsriverorwaterbodynear(rs.getString("isriverorwaterbodynear"));	
		entity.setPurpose(rs.getString("purpose"));	
		entity.setStatus(rs.getString("status"));
		entity.setNextstep(rs.getString("nextstep"));	
		return entity;
	}
}

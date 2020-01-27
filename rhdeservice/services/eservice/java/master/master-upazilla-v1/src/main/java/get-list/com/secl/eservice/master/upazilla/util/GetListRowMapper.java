package com.secl.eservice.master.upazilla.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.master.upazilla.response.GetList;

public class GetListRowMapper implements RowMapper<GetList> {

	public GetList mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetList entity = new GetList();
		entity.setOid(rs.getString("oid"));
		entity.setUpazillaNameEn(rs.getString("upazillaNameEn"));
		entity.setUpazillaNameBn(rs.getString("upazillaNameBn"));		
		return entity;
	}
}

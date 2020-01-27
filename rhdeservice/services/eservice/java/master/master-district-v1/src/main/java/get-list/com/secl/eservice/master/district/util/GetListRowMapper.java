package com.secl.eservice.master.district.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.master.district.response.GetList;

public class GetListRowMapper implements RowMapper<GetList> {

	public GetList mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetList entity = new GetList();
		entity.setOid(rs.getString("oid"));
		entity.setDistrictNameEn(rs.getString("districtNameEn"));
		entity.setDistrictNameBn(rs.getString("districtNameBn"));		
		return entity;
	}
}

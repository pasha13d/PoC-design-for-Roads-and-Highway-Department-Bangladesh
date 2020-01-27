package com.secl.eservice.master.division.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.master.division.response.GetList;

public class GetListRowMapper implements RowMapper<GetList> {

	public GetList mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetList entity = new GetList();
		entity.setOid(rs.getString("oid"));
		entity.setDivisionNameEn(rs.getString("divisionNameEn"));
		entity.setDivisionNameBn(rs.getString("divisionNameBn"));		
		return entity;
	}
}

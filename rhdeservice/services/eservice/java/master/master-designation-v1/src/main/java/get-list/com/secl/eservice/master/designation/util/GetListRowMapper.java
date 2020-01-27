package com.secl.eservice.master.designation.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.master.designation.response.GetList;

public class GetListRowMapper implements RowMapper<GetList> {

	public GetList mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetList entity = new GetList();
		entity.setOid(rs.getString("oid"));
		entity.setDesignationBn(rs.getString("designationBn"));
		entity.setDesignationEn(rs.getString("designationEn"));
		return entity;
	}
}

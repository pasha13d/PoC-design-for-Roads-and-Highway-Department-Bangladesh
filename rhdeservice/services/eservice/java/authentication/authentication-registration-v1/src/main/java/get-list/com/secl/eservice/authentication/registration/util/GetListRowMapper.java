package com.secl.eservice.authentication.registration.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.authentication.registration.response.GetList;

public class GetListRowMapper implements RowMapper<GetList> {

	public GetList mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetList entity = new GetList();
		entity.setOid(rs.getString("oid"));
		entity.setDesignation(rs.getString("designation"));
		entity.setNameBn(rs.getString("nameBn"));
		entity.setNidNo(rs.getString("nidNo"));
		entity.setStatus(rs.getString("status"));
		
		return entity;
	}
}

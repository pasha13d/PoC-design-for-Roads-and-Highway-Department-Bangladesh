package com.secl.eservice.authentication.registration.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.authentication.registration.model.GetByOid;

public class GetByOidRowMapper implements RowMapper<GetByOid> {
	
	public GetByOid mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOid entity = GetByOid.builder()
		.oid(rs.getString("oid"))
		.userName(rs.getString("userName"))
		.password(rs.getString("password"))
		.nameEn(rs.getString("nameen"))
		.nameBn(rs.getString("namebn"))
		.designation(rs.getString("designation"))
		.fatherName(rs.getString("fatherName"))
		.motherName(rs.getString("motherName"))
		.gender(rs.getString("gender"))
		.email(rs.getString("email"))
		.mobileNo(rs.getString("mobileNo"))
		.nidNo(rs.getString("nidNo"))
		.presentAddress(rs.getString("presentAddress"))
		.permanentAddress(rs.getString("permanentAddress"))
		.status(rs.getString("status"))
		.build();
		return entity;
	}
	
}

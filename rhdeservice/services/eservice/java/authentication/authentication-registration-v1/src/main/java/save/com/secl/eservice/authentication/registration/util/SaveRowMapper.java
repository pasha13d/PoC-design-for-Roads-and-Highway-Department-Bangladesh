package com.secl.eservice.authentication.registration.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.authentication.registration.model.SaveRegistration;

public class SaveRowMapper implements RowMapper<SaveRegistration> {
	
	public SaveRegistration mapRow(ResultSet rs, int rowNum) throws SQLException {
		SaveRegistration entity = SaveRegistration.builder()
		.userName(rs.getString("userName"))
		.password(rs.getString("password"))
		.nameBn(rs.getString("nameBn"))
		.nameEn(rs.getString("nameEn"))
		.designation(rs.getString("designation"))
		.fatherName(rs.getString("fatherName"))
		.motherName(rs.getString("motherName"))
		.gender(rs.getString("gender"))
		.dateOfBirth(rs.getDate("dateOfBirth"))
		.email(rs.getString("email"))
		.mobileNo(rs.getString("mobileNo"))
		.nidNo(rs.getString("nidNo"))
		.presentAddress(rs.getString("presentAddress"))
		.permanentAddress(rs.getString("permanentAddress"))
		.build();
		return entity;
	}
	
}

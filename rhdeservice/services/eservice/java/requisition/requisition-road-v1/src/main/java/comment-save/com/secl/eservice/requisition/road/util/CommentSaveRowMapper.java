package com.secl.eservice.requisition.road.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.requisition.road.model.CommentSave;

public class CommentSaveRowMapper implements RowMapper<CommentSave> {
	
	public CommentSave mapRow(ResultSet rs, int rowNum) throws SQLException {
		CommentSave entity = CommentSave.builder()
				.roadRequisitionOid(rs.getString("roadRequisitionOid"))
				.comment(rs.getString("comment"))
				.stepOid(rs.getString("stepOid"))
				.build();
				return entity;
	}
	
}

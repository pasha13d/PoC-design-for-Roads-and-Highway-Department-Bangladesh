package com.secl.eservice.requisition.road.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.secl.eservice.requisition.road.response.GetCommentList;

public class GetCommentListRowMapper implements RowMapper<GetCommentList> {

	public GetCommentList mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetCommentList entity = new GetCommentList();
		entity.setOid(rs.getString("oid"));
		entity.setRoadrequisitionoid(rs.getString("roadrequisitionoid"));
		entity.setComment(rs.getString("comment"));
		entity.setStepoid(rs.getString("stepoid"));
		entity.setCreatedby(rs.getString("createdby"));
		entity.setCreatedon(rs.getString("createdon"));
		return entity;
	}
}

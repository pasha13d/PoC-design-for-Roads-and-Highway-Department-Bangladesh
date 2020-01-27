package com.secl.eservice.requisition.road.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.model.AuthUser;
import com.secl.eservice.requisition.road.request.CommentSaveRequest;
import com.secl.eservice.requisition.road.util.CommentSaveQuery;
import com.secl.eservice.util.IdGenerator;

@Repository("requisitionRoadV1CommentSaveDao")
public class CommentSaveDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;
	
	@Transactional
	public int commentSaveRoad(AuthUser user, CommentSaveRequest request) throws Exception {

		String oid = idGenerator.generateId();

		ImmutablePair<String, Object[]> query = CommentSaveQuery.commentSaveRoadSql(user, request, oid);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());

		return result;
	}
}

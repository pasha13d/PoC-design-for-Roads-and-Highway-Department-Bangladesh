package com.secl.eservice.requisition.road.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.model.AuthUser;
import com.secl.eservice.requisition.road.request.StatusCheckRequest;
import com.secl.eservice.requisition.road.util.StatusCheckQuery;
import com.secl.eservice.util.IdGenerator;

@Repository("requisitionRoadV1StatusCheckDao")
public class StatusCheckDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;
	
	@Transactional
	public int statusCheck(AuthUser user,StatusCheckRequest request) throws Exception {

		ImmutablePair<String, Object[]> query = StatusCheckQuery.statusCheckSql(user,request);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
	
	@Transactional
	public int insertState(AuthUser user,StatusCheckRequest request) throws Exception {
		
		String oid = idGenerator.generateId();
		ImmutablePair<String, Object[]> query = StatusCheckQuery.insertIntoStateSql(user, request, oid);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
	
	@Transactional
	public int isActive(AuthUser user,StatusCheckRequest request) throws Exception {
		
		ImmutablePair<String, Object[]> query = StatusCheckQuery.updateActiveInstance(user, request);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
	
	@Transactional
	public int isDone(AuthUser user,StatusCheckRequest request) throws Exception {
		
		ImmutablePair<String, Object[]> query = StatusCheckQuery.updatedoneInstance(user, request);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
}

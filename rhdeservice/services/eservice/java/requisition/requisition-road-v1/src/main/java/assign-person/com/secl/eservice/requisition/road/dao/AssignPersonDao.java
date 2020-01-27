package com.secl.eservice.requisition.road.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.model.AuthUser;
import com.secl.eservice.requisition.road.model.GetPreviousStepData;
import com.secl.eservice.requisition.road.request.AssignPersonRequest;
import com.secl.eservice.requisition.road.request.StatusCheckRequest;
import com.secl.eservice.requisition.road.util.AssignPersonQuery;
import com.secl.eservice.requisition.road.util.StatusCheckQuery;
import com.secl.eservice.requisition.road.util.StepRowMapper;
import com.secl.eservice.util.IdGenerator;

@Repository("requisitionRoadV1AssignPersonDao")
public class AssignPersonDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;
	

	public List<GetPreviousStepData> stepList = new ArrayList<GetPreviousStepData>() ;
	
	@Transactional
	public List<GetPreviousStepData>  assignPerson(AuthUser user,AssignPersonRequest request) throws Exception {

		ImmutablePair<String, Object[]> query = AssignPersonQuery.assignPersonSql(user,request);
		stepList= jdbcTemplate.query(query.getLeft(), query.getRight(), new StepRowMapper() );
		return ListUtils.emptyIfNull(stepList);
	}
	
	@Transactional
	public int insertState(AuthUser user,AssignPersonRequest request) throws Exception {
		
		String oid = idGenerator.generateId();
		ImmutablePair<String, Object[]> query = AssignPersonQuery.insertIntoStateSql(user, request, oid);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
	
	@Transactional
	public int isActive(AuthUser user,AssignPersonRequest request) throws Exception {
		int result = 0;
		for (GetPreviousStepData ti : stepList) {
			ImmutablePair<String, Object[]> query = AssignPersonQuery.updateActiveInstance(user, request, ti.getOid());
			result = jdbcTemplate.update(query.getLeft(), query.getRight());
			
		}
		return result;
	}
	
	@Transactional
	public int isDone(AuthUser user,AssignPersonRequest request) throws Exception {
		
		ImmutablePair<String, Object[]> query = AssignPersonQuery.updatedoneInstance(user, request);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
	
	
	@Transactional
	public int statusCheck(AuthUser user,AssignPersonRequest request) throws Exception {

		ImmutablePair<String, Object[]> query = AssignPersonQuery.statusCheckSql(user,request);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
}

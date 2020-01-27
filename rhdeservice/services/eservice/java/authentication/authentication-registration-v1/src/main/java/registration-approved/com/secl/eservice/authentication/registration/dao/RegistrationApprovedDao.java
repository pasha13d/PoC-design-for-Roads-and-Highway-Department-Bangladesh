package com.secl.eservice.authentication.registration.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.authentication.registration.request.RegistrationApprovedRequest;
import com.secl.eservice.authentication.registration.util.RegistrationApprovedQuery;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.IdGenerator;

@Repository("authenticationRegistrationV1RegistrationApprovedDao")
public class RegistrationApprovedDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;
	
	@Transactional
	public int registrationApproved(AuthUser user, RegistrationApprovedRequest request) throws Exception {
		

		ImmutablePair<String, Object[]> query = RegistrationApprovedQuery.registrationApprovedSql(user, request);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int insertIntoLogin(AuthUser user, RegistrationApprovedRequest request) throws Exception {
		

		ImmutablePair<String, Object[]> query = RegistrationApprovedQuery.saveIntoLoginSql(user, request);
		int result1 = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result1;
		
	}
}

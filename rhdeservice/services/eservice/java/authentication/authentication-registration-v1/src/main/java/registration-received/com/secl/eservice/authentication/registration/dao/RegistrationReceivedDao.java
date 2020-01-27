package com.secl.eservice.authentication.registration.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.authentication.registration.request.RegistrationReceivedRequest;
import com.secl.eservice.authentication.registration.util.RegistrationReceivedQuery;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.IdGenerator;

@Repository("authenticationRegistrationV1RegistrationReceivedDao")
public class RegistrationReceivedDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;
	
	@Transactional
	public int registrationReceived(AuthUser user, RegistrationReceivedRequest request) throws Exception {
		

		ImmutablePair<String, Object[]> query = RegistrationReceivedQuery.registrationReceivedSql(user, request);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
}

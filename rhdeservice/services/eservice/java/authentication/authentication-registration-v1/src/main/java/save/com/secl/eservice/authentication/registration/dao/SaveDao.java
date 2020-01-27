package com.secl.eservice.authentication.registration.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.authentication.registration.request.SaveRequest;
import com.secl.eservice.authentication.registration.util.SaveQuery;
import com.secl.eservice.util.IdGenerator;

@Repository("authenticationRegistrationV1SaveDao")
public class SaveDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;
	
	@Transactional
	public int saveRegistration(SaveRequest request, String password) throws Exception {

		String oid = idGenerator.generateId();
		

		ImmutablePair<String, Object[]> query = SaveQuery.saveRegistrationSql(request, oid, password);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
}

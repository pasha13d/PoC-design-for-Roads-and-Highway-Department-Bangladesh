package com.secl.eservice.master.step.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.master.step.request.SaveRequisitionInstanceRequest;
import com.secl.eservice.master.step.util.SaveRequisitionInstanceQuery;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.IdGenerator;

@Repository("masterStepV1SaveRequisitionInstanceDao")
public class SaveRequisitionInstanceDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	@Transactional
	public int saveRequisitionInstanceStep(AuthUser user, SaveRequisitionInstanceRequest request, String oid) throws Exception {

		ImmutablePair<String, Object[]> query = SaveRequisitionInstanceQuery.saveRequisitionInstanceSql(user, request, oid);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
	
	@Transactional
	public int updateRequisitionInstanceStep(AuthUser user, SaveRequisitionInstanceRequest request, String oid) throws Exception {

		ImmutablePair<String, Object[]> query = SaveRequisitionInstanceQuery.updateRequisitionInstanceSql(user, request, oid);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		return result;
	}
}

package com.secl.eservice.requisition.road.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.model.AuthUser;
import com.secl.eservice.requisition.road.request.SaveRequest;
import com.secl.eservice.requisition.road.util.SaveQuery;
import com.secl.eservice.util.IdGenerator;

@Repository("requisitionRoadV1SaveDao")
public class SaveDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public int saveRoad(AuthUser user, SaveRequest request, String oid) throws Exception {
		ImmutablePair<String, Object[]> query = SaveQuery.saveRoadSql(user,request, oid);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());

		return result;
	}
}

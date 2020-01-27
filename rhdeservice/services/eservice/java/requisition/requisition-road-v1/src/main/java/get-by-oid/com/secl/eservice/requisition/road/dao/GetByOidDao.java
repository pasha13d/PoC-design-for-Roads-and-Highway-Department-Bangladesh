package com.secl.eservice.requisition.road.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.requisition.road.model.GetByOid;
import com.secl.eservice.requisition.road.request.GetByOidRequest;
import com.secl.eservice.requisition.road.util.GetByOidQuery;
import com.secl.eservice.requisition.road.util.GetByOidRowMapper;
import com.secl.eservice.model.AuthUser;

@Repository("requisitionRoadV1GetByOidDao")
public class GetByOidDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public GetByOid getRoadInfo(AuthUser user, GetByOidRequest request) {
    	ImmutablePair<String, Object[]> query = GetByOidQuery.getOidRoadRequisitionSql(user, request);
    	GetByOid result = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidRowMapper() );
        return result;
    }
    

}

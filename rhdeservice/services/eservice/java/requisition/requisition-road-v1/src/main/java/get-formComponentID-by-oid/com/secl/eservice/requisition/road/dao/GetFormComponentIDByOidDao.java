package com.secl.eservice.requisition.road.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.secl.eservice.requisition.road.model.GetFormComponentIDByOid;
import com.secl.eservice.requisition.road.request.GetFormComponentIDByOidRequest;
import com.secl.eservice.requisition.road.util.GetFormComponentIDByOidQuery;
import com.secl.eservice.requisition.road.util.GetFormComponentIDByOidRowMapper;
import com.secl.eservice.model.AuthUser;

@Repository("requisitionRoadV1GetFormComponentIDByOidDao")
public class GetFormComponentIDByOidDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public GetFormComponentIDByOid getFormInfo(AuthUser user, GetFormComponentIDByOidRequest request) {
    	
    	
    	ImmutablePair<String, Object[]> query = GetFormComponentIDByOidQuery.getFormComponentIdSql(user, request);
    	GetFormComponentIDByOid result = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetFormComponentIDByOidRowMapper());
    	
//    	ImmutablePair<String, Object[]> query1 = GetFormComponentIDByOidQuery.getNextStepNameSql(user, request);
//    	PreviousStepName result1 = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetFormComponentIDByOidRowMapper());

        if(result.equals(null)) {
        	return null;
        }else {
        	
        	return result;
        	
        }
        
    }
    

}

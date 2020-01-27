package com.secl.eservice.authentication.registration.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.authentication.registration.model.GetByOid;
import com.secl.eservice.authentication.registration.request.GetByOidRequest;
import com.secl.eservice.authentication.registration.util.GetByOidQuery;
import com.secl.eservice.authentication.registration.util.GetByOidRowMapper;
import com.secl.eservice.model.AuthUser;

@Repository("authenticationRegistrationV1GetByOidDao")
public class GetByOidDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public GetByOid getRegistrationInfo(AuthUser user, GetByOidRequest request) {
    	ImmutablePair<String, Object[]> query = GetByOidQuery.getRegistrationInfoSql(user, request);
        GetByOid result = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidRowMapper());
        return result;
    }
    

}

package com.secl.eservice.authentication.user.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.authentication.user.model.GetUserInfoByUsernameRole;
import com.secl.eservice.authentication.user.request.GetUserInfoByUsernameRequest;
import com.secl.eservice.authentication.user.util.GetUserInfoByUsernameQuery;
import com.secl.eservice.authentication.user.util.GetUserInfoByUsernameRowMapper;
import com.secl.eservice.model.AuthUser;

@Repository("authenticationUserV1GetListByUsernameDao")
public class GetUserInfoByUsernameDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public GetUserInfoByUsernameRole getUserInfo(AuthUser user, GetUserInfoByUsernameRequest request) {
    	ImmutablePair<String, Object[]> query = GetUserInfoByUsernameQuery.getUserInfoSql(user, request);
        GetUserInfoByUsernameRole result = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetUserInfoByUsernameRowMapper());
        return result;
    }
    

}

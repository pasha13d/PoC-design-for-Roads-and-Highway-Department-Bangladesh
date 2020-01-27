package com.secl.eservice.authentication.registration.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.authentication.registration.response.GetList;
import com.secl.eservice.authentication.registration.util.GetListQuery;
import com.secl.eservice.authentication.registration.util.GetListRowMapper;

@Repository("authenticationRegistrationV1GetListDao")
public class GetListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GetList> getRegistrationAll() {
        ImmutablePair<String, Object[]> query = GetListQuery.getRegistrationListSql();
        List<GetList> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapper());
        return ListUtils.emptyIfNull(result);
    }

}

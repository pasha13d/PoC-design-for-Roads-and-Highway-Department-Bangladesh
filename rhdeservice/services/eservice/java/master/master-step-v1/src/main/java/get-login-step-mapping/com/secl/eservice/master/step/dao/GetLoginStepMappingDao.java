package com.secl.eservice.master.step.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.model.AuthUser;
import com.secl.eservice.master.step.request.GetLoginStepMappingRequest;
import com.secl.eservice.master.step.response.GetLoginStepMapping;
import com.secl.eservice.master.step.util.GetLoginStepMappingQuery;
import com.secl.eservice.master.step.util.GetLoginStepMappingRowMapper;

@Repository("masterStepV1GetLoginStepMappingDao")
public class GetLoginStepMappingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GetLoginStepMapping> getStateAll(AuthUser user, GetLoginStepMappingRequest request) {
        ImmutablePair<String, Object[]> query = GetLoginStepMappingQuery.getLoginStepSql(user, request);
        List<GetLoginStepMapping> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetLoginStepMappingRowMapper());
        return ListUtils.emptyIfNull(result);
    }

}

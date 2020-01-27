package com.secl.eservice.master.step.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.model.AuthUser;
import com.secl.eservice.master.step.request.GetStepListByProcessOidRequest;
import com.secl.eservice.master.step.response.GetStepListByProcessOid;
import com.secl.eservice.master.step.util.GetStepListByProcessOidQuery;
import com.secl.eservice.master.step.util.GetStepListByProcessOidRowMapper;

@Repository("masterStepV1GetStepListByProcessOidDao")
public class GetStepListByProcessOidDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GetStepListByProcessOid> getStateAll(AuthUser user, GetStepListByProcessOidRequest request) {
        ImmutablePair<String, Object[]> query = GetStepListByProcessOidQuery.getStepSql(user, request);
        List<GetStepListByProcessOid> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetStepListByProcessOidRowMapper());
        return ListUtils.emptyIfNull(result);
    }

}

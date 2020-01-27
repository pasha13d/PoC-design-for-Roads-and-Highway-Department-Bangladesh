package com.secl.eservice.master.step.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.model.AuthUser;
import com.secl.eservice.master.step.request.GetInstanceStepListRequest;
import com.secl.eservice.master.step.response.GetInstanceStepList;
import com.secl.eservice.master.step.util.GetInstanceStepListQuery;
import com.secl.eservice.master.step.util.GetInstanceStepListRowMapper;

@Repository("masterStepV1GetInstanceStepListDao")
public class GetInstanceStepListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GetInstanceStepList> getStateAll(AuthUser user, GetInstanceStepListRequest request) {
        ImmutablePair<String, Object[]> query = GetInstanceStepListQuery.getInstanceStepListSql(user, request);
        List<GetInstanceStepList> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetInstanceStepListRowMapper());
        return ListUtils.emptyIfNull(result);
    }

}

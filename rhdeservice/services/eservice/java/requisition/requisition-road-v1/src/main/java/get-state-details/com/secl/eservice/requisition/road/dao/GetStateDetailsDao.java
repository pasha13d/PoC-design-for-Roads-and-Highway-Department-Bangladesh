package com.secl.eservice.requisition.road.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.model.AuthUser;
import com.secl.eservice.requisition.road.request.GetStateDetailsRequest;
import com.secl.eservice.requisition.road.response.GetStateDetails;
import com.secl.eservice.requisition.road.util.GetStateDetailsQuery;
import com.secl.eservice.requisition.road.util.GetStateDetailsRowMapper;

@Repository("requisitionRoadV1GetStateDetailsDao")
public class GetStateDetailsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GetStateDetails> getStateAll(AuthUser user, GetStateDetailsRequest request) {
        ImmutablePair<String, Object[]> query = GetStateDetailsQuery.getRoadSql(user, request);
        List<GetStateDetails> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetStateDetailsRowMapper());
        return ListUtils.emptyIfNull(result);
    }

}

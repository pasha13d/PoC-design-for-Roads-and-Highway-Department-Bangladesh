package com.secl.eservice.requisition.road.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.requisition.road.response.GetList;
import com.secl.eservice.requisition.road.util.GetListQuery;
import com.secl.eservice.requisition.road.util.GetListRowMapper;

@Repository("requisitionRoadV1GetListDao")
public class GetListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GetList> getRoadAll() {
        ImmutablePair<String, Object[]> query = GetListQuery.getRoadSql();
        List<GetList> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapper());
        return ListUtils.emptyIfNull(result);
    }

}

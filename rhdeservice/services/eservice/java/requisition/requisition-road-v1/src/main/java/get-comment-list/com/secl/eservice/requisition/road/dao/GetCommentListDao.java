package com.secl.eservice.requisition.road.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.requisition.road.request.GetCommentListRequest;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.requisition.road.response.GetCommentList;
import com.secl.eservice.requisition.road.util.GetCommentListQuery;
import com.secl.eservice.requisition.road.util.GetCommentListRowMapper;

@Repository("requisitionRoadV1GetCommentListDao")
public class GetCommentListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GetCommentList> getCommentAll(AuthUser user, GetCommentListRequest request) {
        ImmutablePair<String, Object[]> query = GetCommentListQuery.getCommentSql(user, request);
        List<GetCommentList> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetCommentListRowMapper());
        return ListUtils.emptyIfNull(result);
    }
}

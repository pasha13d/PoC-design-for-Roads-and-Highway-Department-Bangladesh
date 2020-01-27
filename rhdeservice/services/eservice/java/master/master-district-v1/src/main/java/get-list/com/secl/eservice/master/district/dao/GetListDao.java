package com.secl.eservice.master.district.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.master.district.request.GetListRequest;
import com.secl.eservice.master.district.response.GetList;
import com.secl.eservice.master.district.util.GetListQuery;
import com.secl.eservice.master.district.util.GetListRowMapper;
import com.secl.eservice.model.AuthUser;

@Repository("masterDistrictV1GetListDao")
public class GetListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GetList> getDistrictAll(AuthUser user, GetListRequest request) {
        ImmutablePair<String, Object[]> query = GetListQuery.getDistrictSql(user, request);
        List<GetList> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapper());
        return ListUtils.emptyIfNull(result);
    }

}

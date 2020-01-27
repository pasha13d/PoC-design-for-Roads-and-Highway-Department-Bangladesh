package com.secl.eservice.master.upazilla.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.master.upazilla.request.GetListRequest;
import com.secl.eservice.master.upazilla.response.GetList;
import com.secl.eservice.master.upazilla.util.GetListQuery;
import com.secl.eservice.master.upazilla.util.GetListRowMapper;
import com.secl.eservice.model.AuthUser;

@Repository("masterUpazillaV1GetListDao")
public class GetListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GetList> getUpazillaAll(AuthUser user, GetListRequest request) {
        ImmutablePair<String, Object[]> query = GetListQuery.getUpazillaSql(user, request);
        List<GetList> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapper());
        return ListUtils.emptyIfNull(result);
    }

}

package com.secl.eservice.master.designation.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.master.designation.response.GetList;
import com.secl.eservice.master.designation.util.GetListQuery;
import com.secl.eservice.master.designation.util.GetListRowMapper;

@Repository("masterDesignationV1GetListDao")
public class GetListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GetList> getDesignationAll() {
        ImmutablePair<String, Object[]> query = GetListQuery.getDesignationSql();
        List<GetList> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapper());
        return ListUtils.emptyIfNull(result);
    }

}

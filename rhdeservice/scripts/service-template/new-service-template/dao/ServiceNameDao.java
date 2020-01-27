package <ServiceNameDaoPackage>.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import <ServiceNameDaoPackage>.response.<ServiceName>;
import <ServiceNameDaoPackage>.util.<ServiceName>Query;
import <ServiceNameDaoPackage>.util.<ServiceName>RowMapper;

@Repository("<SERVICE_PATH_CC>Dao")
public class <ServiceName>Dao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<<ServiceName>> findAll() {
        ImmutablePair<String, Object[]> query = <ServiceName>Query.getMinistrySql();
        List<<ServiceName>> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new <ServiceName>RowMapper());
        return ListUtils.emptyIfNull(result);
    }

}

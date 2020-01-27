package <ServiceNameUtilPackage>.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import <ServiceNameUtilPackage>.response.<ServiceName>;

public class <ServiceName>RowMapper implements RowMapper<<ServiceName>> {

	public <ServiceName> mapRow(ResultSet rs, int rowNum) throws SQLException {
		<ServiceName> entity = new <ServiceName>();
		entity.setOid(rs.getString("oid"));
		entity.setNameEn(rs.getString("name_en"));
		entity.setNameBn(rs.getString("name_bn"));
		return entity;
	}
}

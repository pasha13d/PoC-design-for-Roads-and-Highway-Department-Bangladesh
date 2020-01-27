package <ServiceNameUtilPackage>.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;

import com.secl.eservice.util.constant.Table;
import lombok.Synchronized;

public class <ServiceName>Query {

	@Synchronized
	public static ImmutablePair<String, Object[]> getMinistrySql(){
		String sql = "select oid, name_en, name_bn"
			+ " from " + Table.SCHEMA_CMN + Table.MINISTRY
			+ " where 1 = 1 order by sort_order asc";

        List<Object> data = Lists.newArrayList();
        Object[] aParam = data.toArray(new Object[data.size()]);
        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

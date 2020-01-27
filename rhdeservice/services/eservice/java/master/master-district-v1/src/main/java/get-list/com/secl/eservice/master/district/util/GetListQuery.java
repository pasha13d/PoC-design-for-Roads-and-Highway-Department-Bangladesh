package com.secl.eservice.master.district.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import com.google.common.collect.Lists;
import com.secl.eservice.master.district.request.GetListRequest;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.constant.Table;
import lombok.Synchronized;


public class GetListQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getDistrictSql(AuthUser user, GetListRequest request){
		String sql = "select oid, districtNameBn, districtNameEn"
			+ " from " + Table.DISTRICT
			+ " where 1 = 1 and divisionOid = '" +request.getBody().getOid() + "' order by districtNameEn asc";

        List<Object> data = Lists.newArrayList();
        Object[] aParam = data.toArray(new Object[data.size()]);
        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

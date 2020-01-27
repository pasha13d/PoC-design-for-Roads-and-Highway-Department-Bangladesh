package com.secl.eservice.authentication.registration.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;

import com.secl.eservice.util.constant.Table;
import lombok.Synchronized;

public class GetListQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getRegistrationListSql(){
		String sql = "select oid, nameBn, nidNo, status,(select designationbn from " + Table.DESIGNATION 
						+ " d where d.oid = r.designation) as designation"
						+ " from " + Table.REGISTRATION
						+ " r where 1 = 1 and status='Submitted' order by createdon desc";

        List<Object> data = Lists.newArrayList();
        Object[] aParam = data.toArray(new Object[data.size()]);
        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

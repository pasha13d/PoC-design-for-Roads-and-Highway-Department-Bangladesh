package com.secl.eservice.master.division.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;

import com.secl.eservice.util.constant.Table;
import lombok.Synchronized;

public class GetListQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getDivisionSql(){
		String sql = "select oid, divisionNameBn, divisionNameEn"
			+ " from " + Table.DIVISION
			+ " where 1 = 1 order by divisionNameEn asc";

        List<Object> data = Lists.newArrayList();
        Object[] aParam = data.toArray(new Object[data.size()]);
        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

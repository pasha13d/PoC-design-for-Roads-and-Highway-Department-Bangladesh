package com.secl.eservice.master.step.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.master.step.request.GetStepListByProcessOidRequest;
import com.secl.eservice.util.constant.Table;
import lombok.Synchronized;

public class GetStepListByProcessOidQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getStepSql(AuthUser user, GetStepListByProcessOidRequest request){
		String sql = "select oid from " + Table.STEP + " where status='Active' and processoid='" + request.getBody().getOid() + "' order by sortorder asc";

        List<Object> data = Lists.newArrayList();
        Object[] aParam = data.toArray(new Object[data.size()]);
        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

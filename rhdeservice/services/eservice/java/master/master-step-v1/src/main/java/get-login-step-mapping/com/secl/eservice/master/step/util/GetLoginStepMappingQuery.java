package com.secl.eservice.master.step.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.master.step.request.GetLoginStepMappingRequest;
import com.secl.eservice.util.constant.Table;
import lombok.Synchronized;

public class GetLoginStepMappingQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getLoginStepSql(AuthUser user, GetLoginStepMappingRequest request){
		String sql = "select loginid, stepoid, (select nameBn from "+Table.LOGIN+" where username = lsm.loginid) as name," 
				+ " (select stepName from "+Table.STEP+" where oid = lsm.stepoid ) as stepName,  " 
				+ " (select sortorder from "+Table.STEP+" where oid = lsm.stepoid ) as sortOrder " 
				+ " from "+Table.LOGIN_STEP_MAPPING + " lsm where processoid='" + request.getBody().getOid() + "'";

        List<Object> data = Lists.newArrayList();
        Object[] aParam = data.toArray(new Object[data.size()]);
        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

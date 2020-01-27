package com.secl.eservice.master.step.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.master.step.request.GetInstanceStepListRequest;
import com.secl.eservice.util.constant.Table;
import lombok.Synchronized;

public class GetInstanceStepListQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getInstanceStepListSql(AuthUser user, GetInstanceStepListRequest request){
		String sql = "select st.stepname as text, isdone, isactive from " + Table.REQUISITION_INSTANCE + " ri, " + Table.STEP + " st where st.oid = ri.stepoid and ri.roadrequisitionoid= '" + request.getBody().getOid() + "' order by st.sortorder asc";

        List<Object> data = Lists.newArrayList();
        Object[] aParam = data.toArray(new Object[data.size()]);
        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

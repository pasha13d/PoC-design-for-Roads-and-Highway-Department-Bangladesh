package com.secl.eservice.requisition.road.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.requisition.road.request.GetStateDetailsRequest;
import com.secl.eservice.util.constant.Table;
import lombok.Synchronized;

public class GetStateDetailsQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getRoadSql(AuthUser user, GetStateDetailsRequest request){
		String sql = "select (select stepname from "+Table.STEP+" where oid =rrs.stepoid) as from_step,"
				+ " (select stepname from "+Table.STEP+" where oid =rrs.nextstep ) as to_step,"
				+ " fileName, actualFilename, createdby, createdon"
				+ " from "+Table.ROAD_STATE+" rrs where 1 = 1 and roadrequisitionoid = '"+request.getBody().getOid()+"' order by createdon desc";

        List<Object> data = Lists.newArrayList();
        Object[] aParam = data.toArray(new Object[data.size()]);
        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

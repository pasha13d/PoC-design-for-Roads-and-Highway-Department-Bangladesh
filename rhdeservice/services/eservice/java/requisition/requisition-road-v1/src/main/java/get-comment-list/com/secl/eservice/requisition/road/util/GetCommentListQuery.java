package com.secl.eservice.requisition.road.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;

import com.secl.eservice.util.constant.Table;
import lombok.Synchronized;
import com.secl.eservice.requisition.road.request.GetCommentListRequest;
import com.secl.eservice.model.AuthUser;

public class GetCommentListQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getCommentSql(AuthUser user, GetCommentListRequest request){
		String sql = "select oid, roadrequisitionoid, comment, stepoid, createdby, createdon"
			+ " from " + Table.ROAD_REQUISITION_COMMENT
			+ " where 1 = 1 and roadrequisitionoid = '"+ request.getBody().getOid() +"' order by createdon desc";

        List<Object> data = Lists.newArrayList();
        Object[] aParam = data.toArray(new Object[data.size()]);
        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

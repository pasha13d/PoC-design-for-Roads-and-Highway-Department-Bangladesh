package com.secl.eservice.requisition.road.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.requisition.road.request.CommentSaveRequest;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class CommentSaveQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> commentSaveRoadSql(AuthUser user, CommentSaveRequest request, String oid){
		
			List<String> cols = Lists.newArrayList("oid", "roadrequisitionoid", "comment" , "stepoid", "createdBy");
			List<String> param = Lists.newArrayList("?", "?", "?" , "?", "?");
			List<Object> data = Lists.newArrayList(oid, request.getBody().getComment().getRoadRequisitionOid(), request.getBody().getComment().getComment(), "RRS-4", user.getUsername() );
			
			String sCols = Joiner.on(",").join(cols);
			String sParam = Joiner.on(",").join(param);
			String sql = "insert into " + Table.ROAD_REQUISITION_COMMENT + " (" + sCols + ") values (" + sParam + ")";
	
			Object[] aParam = data.toArray(new Object[data.size()]);
			return new ImmutablePair<String, Object[]>(sql, aParam);
		}
}

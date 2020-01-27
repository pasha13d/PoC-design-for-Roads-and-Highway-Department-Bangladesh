package com.secl.eservice.master.step.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.secl.eservice.master.step.request.SaveRequisitionInstanceRequest;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class SaveRequisitionInstanceQuery {
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveRequisitionInstanceSql(AuthUser user, SaveRequisitionInstanceRequest request, String oid){
			
		List<String> cols = Lists.newArrayList("oid", "roadrequisitionoid", "stepoid", "isdone", "isactive");
		List<String> param = Lists.newArrayList("?", "?", "?", "?" , "?");
		List<Object> data = Lists.newArrayList( oid, request.getBody().getRequisitionInstance().getRoadrequisitionoid(),
												request.getBody().getRequisitionInstance().getStepoid(), request.getBody().getRequisitionInstance().getIsdone(),
												request.getBody().getRequisitionInstance().getIsactive());
		
		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.REQUISITION_INSTANCE+ " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateRequisitionInstanceSql(AuthUser user, SaveRequisitionInstanceRequest request, String oid){
			
		List<Object> param = null;
		String sql = "";	
			param = Lists.newArrayList(request.getBody().getRequisitionInstance().getRoadrequisitionoid());			
		
			sql = " UPDATE " + Table.REQUISITION_INSTANCE + " set isdone = 'true', isActive = 'false' where stepoid = (select oid from "+Table.STEP +" where sortorder = '1') and roadrequisitionoid = ?";			
				
		Object[] data = param.toArray(new Object[param.size()]);
		return new ImmutablePair<String, Object[]>(sql, data);
	}
}

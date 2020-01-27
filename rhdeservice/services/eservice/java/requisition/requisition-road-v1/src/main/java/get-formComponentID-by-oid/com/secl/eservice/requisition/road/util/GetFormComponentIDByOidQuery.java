package com.secl.eservice.requisition.road.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;
import com.secl.eservice.requisition.road.request.GetFormComponentIDByOidRequest;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class GetFormComponentIDByOidQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getFormComponentIdSql(AuthUser user, GetFormComponentIDByOidRequest request){
		
		List<Object> param = Lists.newArrayList(request.getBody().getOid());
		
		String sql = "select oid ,formcomponentid, forward, backward, approve, nextstepoid, previousstepoid from "+Table.STEP+" where 1=1 and oid =( "
				+"select stepoid from "+Table.LOGIN_STEP_MAPPING+" where loginid = '"+user.getUsername()+"' and stepoid = ("
					+"select nextstep from "+Table.ROAD+" where oid = ? ))";
		
		 Object[] data = param.toArray(new Object[param.size()]);
	     return new ImmutablePair<String, Object[]>(sql, data);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getNextStepNameSql(AuthUser user, GetFormComponentIDByOidRequest request){
		
		List<Object> param = Lists.newArrayList(request.getBody().getOid());
		
		String sql = "select stepname from "+Table.STEP+" where oid = ("
				+"select nextstep from "+Table.ROAD+" where oid = ?)";
		
		 Object[] data = param.toArray(new Object[param.size()]);
	     return new ImmutablePair<String, Object[]>(sql, data);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getStepListSql(AuthUser user, GetFormComponentIDByOidRequest request){
		
		List<String> param = Lists.newArrayList(user.getUsername());
		
		String sql = "select stepoid from"+Table.LOGIN_STEP_MAPPING+" where loginId = ?";
		
		 Object[] data = param.toArray(new Object[param.size()]);
	     return new ImmutablePair<String, Object[]>(sql, data);
	}
	

}

package com.secl.eservice.requisition.road.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.requisition.road.request.AssignPersonRequest;
import com.secl.eservice.requisition.road.request.StatusCheckRequest;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class AssignPersonQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> assignPersonSql(AuthUser user,AssignPersonRequest request){
			String sql = "select oid from "+Table.STEP+" where cast(sortorder as int) > cast('"+request.getBody().getStatusCheck().getStep()+"' as int)";
			
	        List<Object> data = Lists.newArrayList();
	        Object[] aParam = data.toArray(new Object[data.size()]);
	        return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateActiveInstance(AuthUser user,AssignPersonRequest request, String stepOid){
			List<Object> param = null;
			String sql = "";	
//		if( request.getBody().getStatusCheck().getStep().equalsIgnoreCase("Next")) {
//			param = Lists.newArrayList(request.getBody().getStatusCheck().getNextStep() , request.getBody().getStatusCheck().getOid());			
//			sql = " UPDATE " + Table.REQUISITION_INSTANCE + " set isActive = 'true', isDone = 'false' WHERE stepoid = ? and roadrequisitionoid = ?";			
//		
//		}else if( request.getBody().getStatusCheck().getStep().equalsIgnoreCase("Previous")){
			
			param = Lists.newArrayList(request.getBody().getStatusCheck().getOid());			
			sql = " UPDATE " + Table.REQUISITION_INSTANCE + " set isActive = 'false', isDone = 'false' WHERE stepoid = '"+stepOid +"' and roadrequisitionoid = ? ";			
		
//		}
//		
//		else {
//			param = Lists.newArrayList(request.getBody().getStatusCheck().getOid());			
//			sql = " UPDATE " + Table.REQUISITION_INSTANCE + " set isActive = 'false', isDone = 'Done' WHERE stepoid = '"+request.getBody().getStatusCheck().getFromstepOid() +"' and roadrequisitionoid = ? ";			
//		
//			
//		}
				
		Object[] data = param.toArray(new Object[param.size()]);
		return new ImmutablePair<String, Object[]>(sql, data);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updatedoneInstance(AuthUser user,AssignPersonRequest request){
			List<Object> param = null;
			String sql = "";	
//		if( request.getBody().getStatusCheck().getStep().equalsIgnoreCase("Next")) {
//			param = Lists.newArrayList(request.getBody().getStatusCheck().getFromstepOid() , request.getBody().getStatusCheck().getOid());			
//			sql = " UPDATE " + Table.REQUISITION_INSTANCE + " set isDone = 'true' , isActive='false' WHERE stepoid = ? and roadrequisitionoid = ?";			
//		
//		}else if( request.getBody().getStatusCheck().getStep().equalsIgnoreCase("Previous")){
			
			param = Lists.newArrayList( request.getBody().getStatusCheck().getOid());
			sql = " UPDATE " + Table.REQUISITION_INSTANCE + " set isDone = 'false', isActive='true' WHERE stepoid = '"+request.getBody().getStatusCheck().getNextStep()+"' and roadrequisitionoid = ? ";			
		
//		}
//		else {
//
//			param = Lists.newArrayList(request.getBody().getStatusCheck().getOid());			
//			sql = " UPDATE " + Table.REQUISITION_INSTANCE + " set isActive = 'false', isDone = 'true' WHERE stepoid = '"+request.getBody().getStatusCheck().getFromstepOid() +"' and roadrequisitionoid = ? ";			
//		
//		}
				
		Object[] data = param.toArray(new Object[param.size()]);
		return new ImmutablePair<String, Object[]>(sql, data);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> insertIntoStateSql(AuthUser user,AssignPersonRequest request, String oid){
			
		List<String> cols = Lists.newArrayList("oid", "roadrequisitionoid","stepoid","nextStep", "fileName", "actualfilename", "createdBy");
		List<String> param = Lists.newArrayList("?", "?", "?" , "?","?" ,"?", "?");
		List<Object> data = Lists.newArrayList(oid, request.getBody().getStatusCheck().getOid(), request.getBody().getStatusCheck().getFromstepOid(), request.getBody().getStatusCheck().getNextStep(), request.getBody().getStatusCheck().getFileName(), request.getBody().getStatusCheck().getActualFilename() ,user.getUsername() );
		
		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.ROAD_STATE + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	
	}
	

	@Synchronized
	public static ImmutablePair<String, Object[]> statusCheckSql(AuthUser user,AssignPersonRequest request){
			List<Object> param = null;
			String sql = "";	
//		if( request.getBody().getStatusCheck().getStep().equalsIgnoreCase("Next")) {
//			param = Lists.newArrayList(request.getBody().getStatusCheck().getNextStep() , request.getBody().getStatusCheck().getOid());			
//			sql = " UPDATE " + Table.ROAD + " set status = (select stepname from "+Table.STEP+" where oid = '"+request.getBody().getStatusCheck().getCurrentStepOid()+"'), nextStep = ? WHERE oid = ? ";			
//		
//		}else if( request.getBody().getStatusCheck().getStep().equalsIgnoreCase("Previous")){
			param = Lists.newArrayList(request.getBody().getStatusCheck().getNextStep() , request.getBody().getStatusCheck().getOid());
			sql = " UPDATE " + Table.ROAD + " set status = (select stepname from "+Table.STEP+" where oid = (select previousstepoid from "+Table.STEP+" where oid ='"+request.getBody().getStatusCheck().getCurrentStepOid() +"')), nextStep = ? WHERE oid = ? ";			
		
//		}else if( request.getBody().getStatusCheck().getStep().equalsIgnoreCase("Previous-Multiple")){
//			param = Lists.newArrayList(request.getBody().getStatusCheck().getNextStep() , request.getBody().getStatusCheck().getOid());
//			sql = " UPDATE " + Table.ROAD + " set status = (select stepname from "+Table.STEP+" where oid = (select previousstepoid from "+Table.STEP+" where oid ='"+request.getBody().getStatusCheck().getCurrentStepOid() +"')), nextStep = ? WHERE oid = ? ";			
//		
//		}else {
//
//			param = Lists.newArrayList(request.getBody().getStatusCheck().getNextStep() , request.getBody().getStatusCheck().getOid());			
//		
//			sql = " UPDATE " + Table.ROAD + " set status = (select stepname from "+Table.STEP+" where oid = '"+request.getBody().getStatusCheck().getCurrentStepOid()+"'), nextStep = ? WHERE oid = ?";			
//			
//		}
				
		Object[] data = param.toArray(new Object[param.size()]);
		return new ImmutablePair<String, Object[]>(sql, data);
	}
	
}

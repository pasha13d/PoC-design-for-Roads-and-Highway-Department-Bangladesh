package com.secl.eservice.requisition.road.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;
import com.secl.eservice.requisition.road.request.GetByOidRequest;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class GetByOidQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getOidRoadRequisitionSql(AuthUser user, GetByOidRequest request){
		
		List<Object> param = Lists.newArrayList(request.getBody().getOid());
		
		String sql = "select oid, " + 
				"(select divisionnamebn from "+ Table.DIVISION +" where oid= rr.divisionoid ) as division," + 
				"(select  districtnamebn from "+ Table.DISTRICT +" where oid= rr.districtoid ) as district," + 
				"(select upazillanamebn from " + Table.UPAZILLA +" where oid= rr.upazillaoid ) as upazilla,"+
				"postalcode, location, startPoint, endPoint, purpose, isriverOrWaterBodyNear, status,nextStep" +
				" from "+Table.ROAD+" rr where rr.oid = ?";
		
		 Object[] data = param.toArray(new Object[param.size()]);
	     return new ImmutablePair<String, Object[]>(sql, data);
	}
	

}

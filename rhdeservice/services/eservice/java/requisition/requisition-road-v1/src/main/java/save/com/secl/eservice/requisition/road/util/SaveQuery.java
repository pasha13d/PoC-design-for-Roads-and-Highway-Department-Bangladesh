package com.secl.eservice.requisition.road.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.requisition.road.request.SaveRequest;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class SaveQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveRoadSql(AuthUser user, SaveRequest request, String oid){
			
			List<String> cols = Lists.newArrayList("oid", "divisionOid", "districtOid" , "upazillaOid", "postalCode", "location", "startPoint", "endPoint", "isriverOrWaterbodynear", "purpose","status","nextStep", "createdBy");
			List<String> param = Lists.newArrayList("?", "?", "?" , "?", "?", "?", "?", "?", "?", "?", "?","?", "?");
			List<Object> data = Lists.newArrayList(oid, request.getBody().getRoad().getDivision(), request.getBody().getRoad().getDistrict(), request.getBody().getRoad().getUpazilla(), request.getBody().getRoad().getPostalCode(),request.getBody().getRoad().getLocation(), 
													request.getBody().getRoad().getStartPoint(), request.getBody().getRoad().getEndPoint(), request.getBody().getRoad().getPurpose(),request.getBody().getRoad().getIsriverOrWaterbodynear(), 
													request.getBody().getRoad().getStatus(), request.getBody().getRoad().getNextStep() , user.getUsername() );
			
			String sCols = Joiner.on(",").join(cols);
			String sParam = Joiner.on(",").join(param);
			String sql = "insert into " + Table.ROAD + " (" + sCols + ") values (" + sParam + ")";
	
			Object[] aParam = data.toArray(new Object[data.size()]);
			return new ImmutablePair<String, Object[]>(sql, aParam);
		}
}

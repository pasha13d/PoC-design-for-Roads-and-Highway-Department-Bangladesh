package com.secl.eservice.authentication.registration.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;
import com.secl.eservice.authentication.registration.request.GetByOidRequest;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class GetByOidQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getRegistrationInfoSql(AuthUser user, GetByOidRequest request){
		
		List<Object> param = Lists.newArrayList(request.getBody().getOid());
		String sql = "select oid, userName, password, nameBn, nameEn, (select designationbn from "+ Table.DESIGNATION +" deg where deg.oid = reg.designation) as designation, fatherName, motherName, gender, dateOfBirth, email, mobileNo, nidNo, presentAddress, permanentAddress, status"
				+ " from " + Table.REGISTRATION
				+ " reg where 1 = 1"
				+ " and oid = ?";
		
		 Object[] data = param.toArray(new Object[param.size()]);
	     return new ImmutablePair<String, Object[]>(sql, data);
	}
	

}

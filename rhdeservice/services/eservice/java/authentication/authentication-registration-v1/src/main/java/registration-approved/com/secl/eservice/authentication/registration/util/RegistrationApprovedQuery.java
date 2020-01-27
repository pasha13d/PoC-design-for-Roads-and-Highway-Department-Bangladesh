package com.secl.eservice.authentication.registration.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;
import com.secl.eservice.authentication.registration.request.RegistrationApprovedRequest;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class RegistrationApprovedQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> registrationApprovedSql(AuthUser user, RegistrationApprovedRequest request){
			
		List<Object> param = Lists.newArrayList(request.getBody().getRegistration().getComment(),
												request.getBody().getRegistration().getOid());
				String sql = "UPDATE " + Table.REGISTRATION + " set status = 'Approved', comment = ? WHERE oid = ?";
				Object[] data = param.toArray(new Object[param.size()]);
				return new ImmutablePair<String, Object[]>(sql, data);
		}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveIntoLoginSql(AuthUser user, RegistrationApprovedRequest request){
			
		List<Object> param = Lists.newArrayList(request.getBody().getRegistration().getOid());
		
				String sql = "INSERT into "+ Table.LOGIN +" (oid, userName, password, nameEn, nameBn, email, mobileNo, menujson, approvedby, roleoid, status) SELECT oid, userName, password, nameEn, nameBn, email, mobileNo,(select accessjson from " + Table.ROLE + " where oid= '" + request.getBody().getRegistration().getRole() + "' ) as menujson, receivedby, '"+ request.getBody().getRegistration().getRole() +"' , 'Active' from "
												+ Table.REGISTRATION +" where oid= ?";
				Object[] data = param.toArray(new Object[param.size()]);
				return new ImmutablePair<String, Object[]>(sql, data);
		}

}

package com.secl.eservice.authentication.registration.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Lists;
import com.secl.eservice.authentication.registration.request.RegistrationReceivedRequest;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class RegistrationReceivedQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> registrationReceivedSql(AuthUser user, RegistrationReceivedRequest request){
			
		List<Object> param = Lists.newArrayList(request.getBody().getRegistration().getComment(),
												request.getBody().getRegistration().getOid());
				String sql = " UPDATE " + Table.REGISTRATION + " set status = 'Submitted', comment = ?, receivedBy = '" + user.getUsername() +"' WHERE oid = ? ";
				Object[] data = param.toArray(new Object[param.size()]);
				return new ImmutablePair<String, Object[]>(sql, data);
		}
}

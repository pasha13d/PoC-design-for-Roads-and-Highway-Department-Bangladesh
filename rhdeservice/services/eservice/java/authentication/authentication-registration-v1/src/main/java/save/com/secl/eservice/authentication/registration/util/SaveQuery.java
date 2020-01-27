package com.secl.eservice.authentication.registration.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.secl.eservice.authentication.registration.request.SaveRequest;
import com.secl.eservice.util.constant.Table;

import lombok.Synchronized;

public class SaveQuery {
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveRegistrationSql(SaveRequest request, String oid, String password){
			
			List<String> cols = Lists.newArrayList("oid", "userName", "password" , "nameBn", "nameEn", "designation", "fatherName", "motherName", "gender", "dateOfBirth", "email", "mobileNo","nidno", "presentAddress", "permanentAddress", "status");
			List<String> param = Lists.newArrayList("?", "?", "?", "?" , "?", "?", "?", "?", "?", "to_date(?, 'YYYY-MM-DD')::date", "?", "?", "?", "?", "?", "?");
			List<Object> data = Lists.newArrayList( oid, request.getBody().getRegistration().getUserName(), password, request.getBody().getRegistration().getNameBn(), 
													request.getBody().getRegistration().getNameEn(), request.getBody().getRegistration().getDesignation(), request.getBody().getRegistration().getFatherName(),
													request.getBody().getRegistration().getMotherName(), request.getBody().getRegistration().getGender(), request.getBody().getRegistration().getDateOfBirth(),
													request.getBody().getRegistration().getEmail(), request.getBody().getRegistration().getMobileNo(), request.getBody().getRegistration().getNidNo(), 
													request.getBody().getRegistration().getPresentAddress(), request.getBody().getRegistration().getPermanentAddress(), request.getBody().getRegistration().getStatus());
			
			String sCols = Joiner.on(",").join(cols);
			String sParam = Joiner.on(",").join(param);
			String sql = "insert into " + Table.REGISTRATION + " (" + sCols + ") values (" + sParam + ")";
	
			Object[] aParam = data.toArray(new Object[data.size()]);
			return new ImmutablePair<String, Object[]>(sql, aParam);
		}
}

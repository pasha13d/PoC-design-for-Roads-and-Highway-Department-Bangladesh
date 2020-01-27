package com.secl.eservice.authentication.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.authentication.registration.dao.RegistrationApprovedDao;
import com.secl.eservice.authentication.registration.request.RegistrationApprovedRequest;
import com.secl.eservice.authentication.registration.response.RegistrationApprovedResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("authenticationRegistrationV1RegistrationApprovedService")
public class RegistrationApprovedService extends BaseService <RegistrationApprovedRequest, RegistrationApprovedResponse>{

	@Autowired
	@Qualifier("authenticationRegistrationV1RegistrationApprovedDao")
	private RegistrationApprovedDao registrationApprovedDao;
	
	@Override
	@Transactional
	public RegistrationApprovedResponse perform(AuthUser user, String url, String version, RegistrationApprovedRequest request, RegistrationApprovedResponse response) throws AppException {
		try {
			registrationApprovedDao.registrationApproved(user, request);
			registrationApprovedDao.insertIntoLogin(user, request);
			log.info("Successfully registrationApproved registration");
			response.getBody().setData(true);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

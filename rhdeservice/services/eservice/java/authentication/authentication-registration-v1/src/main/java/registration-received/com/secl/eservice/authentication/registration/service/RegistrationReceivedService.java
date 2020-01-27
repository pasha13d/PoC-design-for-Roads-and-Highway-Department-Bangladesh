package com.secl.eservice.authentication.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.authentication.registration.dao.RegistrationReceivedDao;
import com.secl.eservice.authentication.registration.request.RegistrationReceivedRequest;
import com.secl.eservice.authentication.registration.response.RegistrationReceivedResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("authenticationRegistrationV1RegistrationReceivedService")
public class RegistrationReceivedService extends BaseService <RegistrationReceivedRequest, RegistrationReceivedResponse>{

	@Autowired
	@Qualifier("authenticationRegistrationV1RegistrationReceivedDao")
	private RegistrationReceivedDao registrationReceivedDao;
	
	@Override
	@Transactional
	public RegistrationReceivedResponse perform(AuthUser user, String url, String version, RegistrationReceivedRequest request, RegistrationReceivedResponse response) throws AppException {
		try {
			registrationReceivedDao.registrationReceived(user, request);
			log.info("Successfully registrationReceived registration");
			response.getBody().setData(true);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

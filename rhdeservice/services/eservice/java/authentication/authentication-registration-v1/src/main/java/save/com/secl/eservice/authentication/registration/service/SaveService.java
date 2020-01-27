package com.secl.eservice.authentication.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.authentication.registration.dao.SaveDao;
import com.secl.eservice.authentication.registration.request.SaveRequest;
import com.secl.eservice.authentication.registration.response.SaveResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("authenticationRegistrationV1SaveService")
public class SaveService extends BaseService <SaveRequest, SaveResponse>{

	@Autowired
	@Qualifier("authenticationRegistrationV1SaveDao")
	private SaveDao saveDao;
	
	@Autowired
	@Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public SaveResponse perform(AuthUser user, String url, String version, SaveRequest request, SaveResponse response) throws AppException {
		try {
			String password  = passwordEncoder.encode(request.getBody().getRegistration().getPassword());
			saveDao.saveRegistration(request, password);
			log.info("Successfully save registration - {}", passwordEncoder.encode(request.getBody().getRegistration().getPassword()));
			response.getBody().setData(true);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

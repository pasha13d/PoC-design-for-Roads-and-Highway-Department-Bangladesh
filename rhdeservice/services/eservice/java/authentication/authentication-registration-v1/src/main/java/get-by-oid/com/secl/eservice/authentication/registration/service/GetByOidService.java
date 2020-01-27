package com.secl.eservice.authentication.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.authentication.registration.dao.GetByOidDao;
import com.secl.eservice.authentication.registration.model.GetByOid;
import com.secl.eservice.authentication.registration.request.GetByOidRequest;
import com.secl.eservice.authentication.registration.response.GetByOidResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("authenticationRegistrationV1GetByOidService")
public class GetByOidService extends BaseService<GetByOidRequest, GetByOidResponse> {

	@Autowired
	@Qualifier("authenticationRegistrationV1GetByOidDao")
	private GetByOidDao getListDao;
	
	@Override
	public GetByOidResponse perform(AuthUser user, String url, String version, GetByOidRequest request, GetByOidResponse response) throws AppException {
		try {
			GetByOid userInfo = getListDao.getRegistrationInfo(user, request); 
			log.info("Successfully got user information - {}", userInfo);
			response.getBody().setData(userInfo);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

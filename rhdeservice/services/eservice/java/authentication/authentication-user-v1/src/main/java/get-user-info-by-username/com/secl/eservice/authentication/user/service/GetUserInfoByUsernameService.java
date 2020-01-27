package com.secl.eservice.authentication.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.authentication.user.dao.GetUserInfoByUsernameDao;
import com.secl.eservice.authentication.user.model.GetUserInfoByUsernameRole;
import com.secl.eservice.authentication.user.request.GetUserInfoByUsernameRequest;
import com.secl.eservice.authentication.user.response.GetUserInfoByUsernameResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("authenticationUserV1GetListByUsernameService")
public class GetUserInfoByUsernameService extends BaseService<GetUserInfoByUsernameRequest, GetUserInfoByUsernameResponse> {

	@Autowired
	@Qualifier("authenticationUserV1GetListByUsernameDao")
	private GetUserInfoByUsernameDao getListDao;
	
	@Override
	public GetUserInfoByUsernameResponse perform(AuthUser user, String url, String version, GetUserInfoByUsernameRequest request, GetUserInfoByUsernameResponse response) throws AppException {
		try {
			GetUserInfoByUsernameRole userInfo = getListDao.getUserInfo(user, request); 
			log.info("Successfully got user information - {}", userInfo);
			response.getBody().setData(userInfo);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

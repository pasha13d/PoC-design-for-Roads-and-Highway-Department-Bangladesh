package com.secl.eservice.master.step.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

import com.secl.eservice.master.step.dao.GetLoginStepMappingDao;
import com.secl.eservice.master.step.request.GetLoginStepMappingRequest;
import com.secl.eservice.master.step.response.GetLoginStepMapping;
import com.secl.eservice.master.step.response.GetLoginStepMappingResponse;

import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("masterStepV1GetLoginStepMappingService")
public class GetLoginStepMappingService extends BaseService<GetLoginStepMappingRequest, GetLoginStepMappingResponse> {

	@Autowired
	@Qualifier("masterStepV1GetLoginStepMappingDao")
	private GetLoginStepMappingDao getLoginStepMappingDao;

	@Override
	public GetLoginStepMappingResponse perform(AuthUser user, String url, String version, GetLoginStepMappingRequest request, GetLoginStepMappingResponse response) throws AppException {
		try {
			List<GetLoginStepMapping> stateList = getLoginStepMappingDao.getStateAll(user, request);
			log.info("login step count - {}", stateList.size());
			response.getBody().setData(stateList);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
}

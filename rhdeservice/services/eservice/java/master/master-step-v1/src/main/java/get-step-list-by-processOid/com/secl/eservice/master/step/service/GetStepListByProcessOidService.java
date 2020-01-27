package com.secl.eservice.master.step.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

import com.secl.eservice.master.step.dao.GetStepListByProcessOidDao;
import com.secl.eservice.master.step.request.GetStepListByProcessOidRequest;
import com.secl.eservice.master.step.response.GetStepListByProcessOid;
import com.secl.eservice.master.step.response.GetStepListByProcessOidResponse;

import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("masterStepV1GetStepListByProcessOidService")
public class GetStepListByProcessOidService extends BaseService<GetStepListByProcessOidRequest, GetStepListByProcessOidResponse> {

	@Autowired
	@Qualifier("masterStepV1GetStepListByProcessOidDao")
	private GetStepListByProcessOidDao getStepListByProcessOidDao;

	@Override
	public GetStepListByProcessOidResponse perform(AuthUser user, String url, String version, GetStepListByProcessOidRequest request, GetStepListByProcessOidResponse response) throws AppException {
		try {
			List<GetStepListByProcessOid> stateList = getStepListByProcessOidDao.getStateAll(user, request);
			log.info("process count - {}", stateList.size());
			response.getBody().setData(stateList);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
}

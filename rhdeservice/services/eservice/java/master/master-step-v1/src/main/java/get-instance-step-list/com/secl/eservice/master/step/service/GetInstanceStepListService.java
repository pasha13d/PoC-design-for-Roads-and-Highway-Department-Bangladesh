package com.secl.eservice.master.step.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

import com.secl.eservice.master.step.dao.GetInstanceStepListDao;
import com.secl.eservice.master.step.request.GetInstanceStepListRequest;
import com.secl.eservice.master.step.response.GetInstanceStepList;
import com.secl.eservice.master.step.response.GetInstanceStepListResponse;

import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("masterStepV1GetInstanceStepListService")
public class GetInstanceStepListService extends BaseService<GetInstanceStepListRequest, GetInstanceStepListResponse> {

	@Autowired
	@Qualifier("masterStepV1GetInstanceStepListDao")
	private GetInstanceStepListDao getInstanceStepListDao;

	@Override
	public GetInstanceStepListResponse perform(AuthUser user, String url, String version, GetInstanceStepListRequest request, GetInstanceStepListResponse response) throws AppException {
		try {
			List<GetInstanceStepList> stateList = getInstanceStepListDao.getStateAll(user, request);
			log.info("instance step list count - {}", stateList.size());
			response.getBody().setData(stateList);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
}

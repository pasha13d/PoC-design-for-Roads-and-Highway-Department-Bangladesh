package com.secl.eservice.requisition.road.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

import com.secl.eservice.requisition.road.dao.GetStateDetailsDao;
import com.secl.eservice.requisition.road.request.GetStateDetailsRequest;
import com.secl.eservice.requisition.road.response.GetStateDetails;
import com.secl.eservice.requisition.road.response.GetStateDetailsResponse;

import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("requisitionRoadV1GetStateDetailsService")
public class GetStateDetailsService extends BaseService<GetStateDetailsRequest, GetStateDetailsResponse> {

	@Autowired
	@Qualifier("requisitionRoadV1GetStateDetailsDao")
	private GetStateDetailsDao getStateDetailsDao;

	@Override
	public GetStateDetailsResponse perform(AuthUser user, String url, String version, GetStateDetailsRequest request, GetStateDetailsResponse response) throws AppException {
		try {
			List<GetStateDetails> stateList = getStateDetailsDao.getStateAll(user, request);
			log.info("division count - {}", stateList.size());
			response.getBody().setData(stateList);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
}

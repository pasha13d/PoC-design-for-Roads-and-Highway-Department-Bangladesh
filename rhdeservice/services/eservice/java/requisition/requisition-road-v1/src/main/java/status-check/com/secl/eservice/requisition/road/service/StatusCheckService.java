package com.secl.eservice.requisition.road.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.secl.eservice.requisition.road.dao.StatusCheckDao;
import com.secl.eservice.requisition.road.request.StatusCheckRequest;
import com.secl.eservice.requisition.road.response.StatusCheckResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("requisitionRoadV1StatusCheckService")
public class StatusCheckService extends BaseService <StatusCheckRequest, StatusCheckResponse>{

	@Autowired
	@Qualifier("requisitionRoadV1StatusCheckDao")
	private StatusCheckDao statusCheckDao;
	
	@Override
	@Transactional
	public StatusCheckResponse perform(AuthUser user, String url, String version, StatusCheckRequest request,StatusCheckResponse response) throws AppException {
		try {
			statusCheckDao.statusCheck(user,request);
			statusCheckDao.insertState(user, request);
			statusCheckDao.isActive(user, request);
			statusCheckDao.isDone(user, request);
			log.info("Successfully status updated to Check for road");
			response.getBody().setData(true);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

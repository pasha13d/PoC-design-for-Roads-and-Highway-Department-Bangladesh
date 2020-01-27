package com.secl.eservice.requisition.road.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.secl.eservice.requisition.road.dao.AssignPersonDao;
import com.secl.eservice.requisition.road.request.AssignPersonRequest;
import com.secl.eservice.requisition.road.response.AssignPersonResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("requisitionRoadV1AssignPersonService")
public class AssignPersonService extends BaseService <AssignPersonRequest, AssignPersonResponse>{

	@Autowired
	@Qualifier("requisitionRoadV1AssignPersonDao")
	private AssignPersonDao assignPersonDao;
	
	@Override
	@Transactional
	public AssignPersonResponse perform(AuthUser user, String url, String version, AssignPersonRequest request,AssignPersonResponse response) throws AppException {
		try {
			assignPersonDao.assignPerson(user,request);
			assignPersonDao.insertState(user, request);
			assignPersonDao.isActive(user, request);
			assignPersonDao.isDone(user, request);
			assignPersonDao.statusCheck(user,request);
			log.info("Successfully status updated to Check for road");
			response.getBody().setData(true);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

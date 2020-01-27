package com.secl.eservice.requisition.road.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.requisition.road.dao.SaveDao;
import com.secl.eservice.requisition.road.request.SaveRequest;
import com.secl.eservice.requisition.road.response.SaveResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.IdGenerator;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("requisitionRoadV1SaveService")
public class SaveService extends BaseService <SaveRequest, SaveResponse>{

	@Autowired
	@Qualifier("requisitionRoadV1SaveDao")
	private SaveDao saveDao;
	
	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;
	
	
	@Override
	@Transactional
	public SaveResponse perform(AuthUser user, String url, String version, SaveRequest request, SaveResponse response) throws AppException {
		try {
			String oid = idGenerator.generateId();

			saveDao.saveRoad(user,request, oid);
			log.info("Successfully save Road");
			response.getBody().setData(oid);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

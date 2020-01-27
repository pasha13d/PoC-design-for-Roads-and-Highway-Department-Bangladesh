package com.secl.eservice.requisition.road.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.requisition.road.dao.CommentSaveDao;
import com.secl.eservice.requisition.road.request.CommentSaveRequest;
import com.secl.eservice.requisition.road.response.CommentSaveResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("requisitionRoadV1CommentSaveService")
public class CommentSaveService extends BaseService <CommentSaveRequest, CommentSaveResponse>{

	@Autowired
	@Qualifier("requisitionRoadV1CommentSaveDao")
	private CommentSaveDao commentSaveDao;
	
	@Override
	@Transactional
	public CommentSaveResponse perform(AuthUser user, String url, String version, CommentSaveRequest request, CommentSaveResponse response) throws AppException {
		try {
			commentSaveDao.commentSaveRoad(user,request);
			log.info("Successfully commentSave Road");
			response.getBody().setData(true);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

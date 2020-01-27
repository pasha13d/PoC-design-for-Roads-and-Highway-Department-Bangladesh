package com.secl.eservice.requisition.road.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

import com.secl.eservice.requisition.road.dao.GetCommentListDao;
import com.secl.eservice.requisition.road.request.GetCommentListRequest;
import com.secl.eservice.requisition.road.response.GetCommentList;
import com.secl.eservice.requisition.road.response.GetCommentListResponse;

import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("requisitionRoadV1GetCommentListService")
public class GetCommentListService extends BaseService<GetCommentListRequest, GetCommentListResponse> {

	@Autowired
	@Qualifier("requisitionRoadV1GetCommentListDao")
	private GetCommentListDao getCommentListDao;

	@Override
	public GetCommentListResponse perform(AuthUser user, String url, String version, GetCommentListRequest request, GetCommentListResponse response) throws AppException {
		try {
			List<GetCommentList> commentList = getCommentListDao.getCommentAll(user, request);
			log.info("comment count - {}", commentList.size());
			response.getBody().setData(commentList);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
}

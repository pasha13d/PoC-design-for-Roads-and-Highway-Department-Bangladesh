package com.secl.eservice.requisition.road.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

import com.secl.eservice.requisition.road.dao.GetListDao;
import com.secl.eservice.requisition.road.request.GetListRequest;
import com.secl.eservice.requisition.road.response.GetList;
import com.secl.eservice.requisition.road.response.GetListResponse;

import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("requisitionRoadV1GetListService")
public class GetListService extends BaseService<GetListRequest, GetListResponse> {

	@Autowired
	@Qualifier("requisitionRoadV1GetListDao")
	private GetListDao getListDao;

	@Override
	public GetListResponse perform(AuthUser user, String url, String version, GetListRequest request, GetListResponse response) throws AppException {
		try {
			List<GetList> roadRequisitionList = getListDao.getRoadAll();
			log.info("division count - {}", roadRequisitionList.size());
			response.getBody().setData(roadRequisitionList);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
}

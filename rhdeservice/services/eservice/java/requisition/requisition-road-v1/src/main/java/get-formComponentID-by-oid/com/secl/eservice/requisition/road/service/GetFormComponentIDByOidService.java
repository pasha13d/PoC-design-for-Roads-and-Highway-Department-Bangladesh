package com.secl.eservice.requisition.road.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.requisition.road.dao.GetFormComponentIDByOidDao;
import com.secl.eservice.requisition.road.model.GetFormComponentIDByOid;
import com.secl.eservice.requisition.road.request.GetFormComponentIDByOidRequest;
import com.secl.eservice.requisition.road.response.GetFormComponentIDByOidResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("requisitionRoadV1GetFormComponentIDByOidService")
public class GetFormComponentIDByOidService extends BaseService<GetFormComponentIDByOidRequest, GetFormComponentIDByOidResponse> {

	@Autowired
	@Qualifier("requisitionRoadV1GetFormComponentIDByOidDao")
	private GetFormComponentIDByOidDao getListDao;
	
	@Override
	public GetFormComponentIDByOidResponse perform(AuthUser user, String url, String version, GetFormComponentIDByOidRequest request, GetFormComponentIDByOidResponse response) throws AppException {
		try {
			GetFormComponentIDByOid userInfo = getListDao.getFormInfo(user, request); 
			log.info("Successfully got user information - {}", userInfo);
			response.getBody().setData(userInfo);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

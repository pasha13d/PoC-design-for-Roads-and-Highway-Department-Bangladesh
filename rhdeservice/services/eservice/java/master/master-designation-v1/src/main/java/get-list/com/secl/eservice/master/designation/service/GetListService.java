package com.secl.eservice.master.designation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

import com.secl.eservice.master.designation.dao.GetListDao;
import com.secl.eservice.master.designation.request.GetListRequest;
import com.secl.eservice.master.designation.response.GetList;
import com.secl.eservice.master.designation.response.GetListResponse;

import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("masterDesignationV1GetListService")
public class GetListService extends BaseService<GetListRequest, GetListResponse> {

	@Autowired
	@Qualifier("masterDesignationV1GetListDao")
	private GetListDao getListDao;

	@Override
	public GetListResponse perform(AuthUser user, String url, String version, GetListRequest request, GetListResponse response) throws AppException {
		try {
			List<GetList> designationList = getListDao.getDesignationAll();
			log.info("designation count - {}", designationList.size());
			response.getBody().setData(designationList);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
}

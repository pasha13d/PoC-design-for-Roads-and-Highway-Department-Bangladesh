package com.secl.eservice.master.upazilla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

import com.secl.eservice.master.upazilla.dao.GetListDao;
import com.secl.eservice.master.upazilla.request.GetListRequest;
import com.secl.eservice.master.upazilla.response.GetList;
import com.secl.eservice.master.upazilla.response.GetListResponse;

import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("masterUpazillaV1GetListService")
public class GetListService extends BaseService<GetListRequest, GetListResponse> {

	@Autowired
	@Qualifier("masterUpazillaV1GetListDao")
	private GetListDao getListDao;

	@Override
	public GetListResponse perform(AuthUser user, String url, String version, GetListRequest request, GetListResponse response) throws AppException {
		try {
			List<GetList> upazillaList = getListDao.getUpazillaAll(user, request);
			log.info("upazilla count - {}", upazillaList.size());
			response.getBody().setData(upazillaList);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
}

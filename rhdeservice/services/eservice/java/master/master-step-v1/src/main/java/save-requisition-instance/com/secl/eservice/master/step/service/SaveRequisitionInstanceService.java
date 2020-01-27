package com.secl.eservice.master.step.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secl.eservice.master.step.dao.SaveRequisitionInstanceDao;
import com.secl.eservice.master.step.request.SaveRequisitionInstanceRequest;
import com.secl.eservice.master.step.response.SaveRequisitionInstanceResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.IdGenerator;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("masterStepV1SaveRequisitionInstanceService")
public class SaveRequisitionInstanceService extends BaseService <SaveRequisitionInstanceRequest, SaveRequisitionInstanceResponse>{

	@Autowired
	@Qualifier("masterStepV1SaveRequisitionInstanceDao")
	private SaveRequisitionInstanceDao saveRequisitionInstanceDao;
	
	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;
	
	@Override
	@Transactional
	public SaveRequisitionInstanceResponse perform(AuthUser user, String url, String version, SaveRequisitionInstanceRequest request, SaveRequisitionInstanceResponse response) throws AppException {
		try {
			
			String oid = idGenerator.generateId();
			saveRequisitionInstanceDao.saveRequisitionInstanceStep(user, request, oid);
			saveRequisitionInstanceDao.updateRequisitionInstanceStep(user, request, oid);
			log.info("Successfully saveRequisitionInstance - {}");
			response.getBody().setData(true);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

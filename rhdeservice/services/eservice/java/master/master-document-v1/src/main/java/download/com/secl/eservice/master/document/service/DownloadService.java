package com.secl.eservice.master.document.service;

import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

import com.secl.eservice.master.document.request.DownloadRequest;
import com.secl.eservice.master.document.response.DownloadResponse;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

@Service("masterDocumentV1DonwloadService")
public class DownloadService extends BaseService<DownloadRequest, DownloadResponse> {

	@Override
	public DownloadResponse perform(AuthUser user, String url, String version, DownloadRequest request, DownloadResponse response) throws AppException {
		try {
			response.getBody().setFilePath(request.getBody().getFileName());;
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
}

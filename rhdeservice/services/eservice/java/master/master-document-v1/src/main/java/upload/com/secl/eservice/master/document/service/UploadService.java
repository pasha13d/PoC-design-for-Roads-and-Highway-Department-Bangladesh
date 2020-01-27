package com.secl.eservice.master.document.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.secl.eservice.master.document.dao.UploadDao;
import com.secl.eservice.master.document.request.UploadRequest;
import com.secl.eservice.master.document.response.UploadResponse;
import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.IdGenerator;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("masterDocumentV1UploadService")
public class UploadService extends BaseService <UploadRequest, UploadResponse>{

	private Path fileStorageLocation;
	
	@Autowired
	private Environment env;
	
	@Autowired
	@Qualifier("masterDocumentV1UploadDao")
	private UploadDao uploadDao;
	
	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;
	
    @PostConstruct
    public void init() {
        try {
        	String dir = env.getProperty("file.upload-dir");
        	this.fileStorageLocation = Paths.get(dir).toAbsolutePath().normalize();
        	Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            log.error("An exception occurred while create directory : ", ex);
        }
    }
	
	@Override
	@Transactional
	public UploadResponse perform(AuthUser user, String url, String version, UploadRequest request, UploadResponse response) throws AppException {
		try {
			
			MultipartFile file = request.getBody().getFile();
			String fileName = idGenerator.generateId() + "_" + StringUtils.cleanPath(file.getOriginalFilename());

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
//            uploadDao.uploadDocument(user, request.getBody(), fileName);
		
			log.info("Successfully store file");
			response.getBody().setFileName(fileName);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully store file");

		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}

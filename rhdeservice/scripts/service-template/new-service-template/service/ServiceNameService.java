package <ServiceNameServicePackage>.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

import <ServiceNameServicePackage>.dao.<ServiceName>Dao;
import <ServiceNameServicePackage>.request.<ServiceName>Request;
import <ServiceNameServicePackage>.response.<ServiceName>;
import <ServiceNameServicePackage>.response.<ServiceName>Response;

import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("<SERVICE_PATH_CC>Service")
public class <ServiceName>Service extends BaseService<<ServiceName>Request, <ServiceName>Response> {

	@Autowired
	@Qualifier("<SERVICE_PATH_CC>Dao")
	private <ServiceName>Dao <SERVICE_CC_LOWER>Dao;

	@Override
	public <ServiceName>Response perform(AuthUser user, String url, String version, <ServiceName>Request request, <ServiceName>Response response) throws AppException {
		try {
			List<<ServiceName>> <FeatureName>List = <SERVICE_CC_LOWER>Dao.findAll();
			log.info("<FeatureName> count - {}", <FeatureName>List.size());
			response.getBody().setData(<FeatureName>List);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
}

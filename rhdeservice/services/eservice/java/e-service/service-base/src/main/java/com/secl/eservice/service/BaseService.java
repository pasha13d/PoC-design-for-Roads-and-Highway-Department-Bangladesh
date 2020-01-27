package com.secl.eservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.model.AuthUser;

public abstract class BaseService<A, B> {

	@Autowired
	protected ApplicationEventPublisher publisher;
    
    @Autowired
    protected ApplicationContext ctx;

	public abstract B perform(AuthUser user, String url, String version, A request, B response) throws AppException;

}

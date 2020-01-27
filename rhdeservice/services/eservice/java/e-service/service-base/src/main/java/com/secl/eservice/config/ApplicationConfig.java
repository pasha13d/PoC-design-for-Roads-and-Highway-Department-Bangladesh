package com.secl.eservice.config;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ApplicationConfig {
	
	@PreDestroy
	public void onShutDown() {
		log.warn("Application closing...");
	}
	
}
package com.secl.eservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableCaching
@SpringBootApplication
@ComponentScan("com.secl.eservice")
@EnableTransactionManagement
public class EServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EServiceApplication.class, args);
	}
}

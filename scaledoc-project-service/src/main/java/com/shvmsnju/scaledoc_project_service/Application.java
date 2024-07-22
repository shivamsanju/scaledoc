package com.shvmsnju.scaledoc_project_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka // Enabled by default by SpringBoot
public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		LOG.info(String.format("Server started on port %s", 8080));
		SpringApplication.run(Application.class, args);
	}

}

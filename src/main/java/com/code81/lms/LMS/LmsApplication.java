package com.code81.lms.LMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.code81.lms", "controller", "service", "repository", "entity", "dto", "mapper", "config", "util"})
@EnableJpaRepositories(basePackages = {"repository"})
@EntityScan(basePackages = {"entity"})
public class LmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
	}

}

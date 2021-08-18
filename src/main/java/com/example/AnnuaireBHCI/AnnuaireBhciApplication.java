package com.example.AnnuaireBHCI;

import org.apache.poi.sl.usermodel.ObjectMetaData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AnnuaireBhciApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AnnuaireBhciApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(AnnuaireBhciApplication.class, args);
	}

}

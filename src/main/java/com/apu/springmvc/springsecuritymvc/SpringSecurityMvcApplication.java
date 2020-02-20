package com.apu.springmvc.springsecuritymvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*@SpringBootApplication
public class SpringSecurityMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityMvcApplication.class, args);
	}

}*/

//for wildfly server
@SpringBootApplication
public class SpringSecurityMvcApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringSecurityMvcApplication.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityMvcApplication.class, args);
	}

}
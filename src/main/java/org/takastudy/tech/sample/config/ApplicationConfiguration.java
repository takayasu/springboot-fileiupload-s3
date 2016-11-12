package org.takastudy.tech.sample.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public EmbeddedServletContainerFactory configuration(){
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
	 
		factory.setContextPath("/aws");
		
		return factory;
	}
	
}

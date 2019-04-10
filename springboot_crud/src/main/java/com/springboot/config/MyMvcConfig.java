package com.springboot.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * If you want to take complete control of Spring MVC, you can add your
 * own @Configuration annotated with @EnableWebMvc.
 */
// @EnableWebMvc
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("test").setViewName("test");
		registry.addViewController("/").setViewName("login");
		registry.addViewController("/index").setViewName("login");
		registry.addViewController("/index.html").setViewName("login");
	}

	/**
	 * If you wish to provide custom instances of RequestMappingHandlerMapping,
	 * RequestMappingHandlerAdapter, or ExceptionHandlerExceptionResolver, you
	 * can declare a WebMvcRegistrations(this is a interface,the offical
	 * document privided is WebMvcRegistrationsAdapter which has something
	 * wrong) instance to provide such components.
	 */
	@Bean
	public WebMvcRegistrations getWebMvcRegistrationsAdapter() {
		return new WebMvcRegistrations() {

		};
	}

}

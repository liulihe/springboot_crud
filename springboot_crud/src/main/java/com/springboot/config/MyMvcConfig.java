package com.springboot.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springboot.specifiedcomponeng.LoginInterceptor;
import com.springboot.specifiedcomponeng.MyLocaleResolver;

/**
 * If you want to take complete control of Spring MVC, you can add your
 * own @Configuration annotated with @EnableWebMvc.
 */
// @EnableWebMvc
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

	/**
	 * access url return to specified view
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
		registry.addViewController("/index.html").setViewName("login");
		registry.addViewController("/main.html").setViewName("dashboard");
		WebMvcConfigurer.super.addViewControllers(registry);
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

	/**
	 * use specified LocaleResolver and not use springboot autoconfiguration
	 * LocaleResolver
	 */
	@Bean
	public LocaleResolver getLocaleResolver() {
		return new MyLocaleResolver();
	}

	/**
	 * given specified interceptor do login validation,exclude static resources
	 * and specified login url
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/", "/index.html",
				"/login.html", "/webjars/**", "/static/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	/**
	 * indicated static resources
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}
}
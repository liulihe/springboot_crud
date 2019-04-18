package com.springboot.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.springboot.component.LoginInterceptor;
import com.springboot.component.MyLocaleResolver;

/**
 * If you want to take complete control of Spring MVC, you can add your
 * own @Configuration annotated with @EnableWebMvc.
 */
// @EnableWebMvc
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

	/**
	 * 制定url和视图映射
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
	 * 
	 * 添加一个LocaleResolver
	 */
	@Bean
	public LocaleResolver getLocaleResolver() {
		return new MyLocaleResolver();
	}

	/**
	 * 添加登录拦截器，排除静态资源的路径，登录相关的路径
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/", "/index.html",
				"/login.html", "/webjars/**", "/static/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	/**
	 * 指定静态文件
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	/**
	 * 使用阿里巴巴德鲁伊数据库连接池，使用自定义的bean，否则，在yml中配置的druid连接池的配置无效
	 */
	@Bean
	@ConfigurationProperties("spring.datasource")
	public DataSource getDruidDataSource() {
		return new DruidDataSource();
	}
}
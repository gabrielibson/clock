package com.liferay.clock.api.config;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport{
	@Autowired
	private TypeResolver typeResolver;
	
	@Bean
	  public Docket clockApi() {
	    return new Docket(DocumentationType.SWAGGER_2)
	    	.alternateTypeRules( AlternateTypeRules.newRule(
	                  typeResolver.resolve(Collection.class, LocalDateTime.class),
	                  typeResolver.resolve(Collection.class, Date.class), Ordered.HIGHEST_PRECEDENCE))
	        .select()
	        .apis(RequestHandlerSelectors.basePackage("com.liferay.clock.api.controller"))
	        .paths(PathSelectors.regex("/.*")).build()
	        .apiInfo(metaData());

	  }

	  private ApiInfo metaData() {
	    return new ApiInfoBuilder()
	        .title("Spring Boot REST API")
	        .description("\"Spring Boot REST API for control time sheet with its registers.\"")
	        .version("1.0.0")
	        .license("Apache License Version 2.0")
	        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
	        .build();
	  }

	  @Override
	  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("swagger-ui.html")
	        .addResourceLocations("classpath:/META-INF/resources/");

	    registry.addResourceHandler("/webjars/**")
	        .addResourceLocations("classpath:/META-INF/resources/webjars/");
	  }
}

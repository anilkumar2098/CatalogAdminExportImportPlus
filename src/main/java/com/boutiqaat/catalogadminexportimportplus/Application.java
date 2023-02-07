package com.boutiqaat.catalogadminexportimportplus;

//import com.boutiqaat.catalogadmin.annotation.LogPerformanceData;
import com.google.common.base.Stopwatch;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;


import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@EntityScan(basePackages = {"com.boutiqaat.catalogadminexportimportplus"})
//@EnableAsync
//@EnableCaching
//@EnableJpaAuditing
@EnableConfigurationProperties

@EnableSpringDataWebSupport
//@EnableFeignClients(basePackages = "com.boutiqaat.catalogadmin")
@ComponentScan(basePackages = {
		"com.boutiqaat.catalogadminexportimportplus"})
public class Application extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@PostConstruct
	public void init() {
		// Setting Default TimeZone to Kuwait
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kuwait"));
	}

   // @LogPerformanceData
	public static void main(String[] args) {
		Stopwatch timer = Stopwatch.createStarted();
		SpringApplication.run(Application.class, args);
		logger.debug("server started successfully in :[{}]", timer.stop());
	}

	/**
	 * @return ModelMapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}

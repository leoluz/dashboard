package com.github.leoluz.dashboard

import com.github.leoluz.dashboard.web.OauthFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

	@Autowired
	private Config config

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean()
		OauthFilter oauthVerifier = new OauthFilter()
		filterRegistrationBean.setFilter(oauthVerifier)
		filterRegistrationBean.addUrlPatterns("/api/*")
		filterRegistrationBean.addInitParameter("consumerSecret", config.CONSUMER_SECRET)
		filterRegistrationBean
	}

	static void main(String[] args) {
		SpringApplication.run Application, args
	}
}

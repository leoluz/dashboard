package com.github.leoluz.dashboard.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping("/api/subscriptions")
class Subscription {

	private static final Logger logger = LoggerFactory.getLogger(Subscription.class)

	@RequestMapping(value="/hi/{name}", method=GET)
	String home(@PathVariable String name) {
		logger.info ">> name: ${name}"
		"Hello! My name is ${name}"
	}

	@RequestMapping(value="/create}")
	String create(@RequestParam(value="url", required=true) String url) {
		logger.info ">> url: ${url}"
		url
	}
}

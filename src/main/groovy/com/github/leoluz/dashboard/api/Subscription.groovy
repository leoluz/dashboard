package com.github.leoluz.dashboard.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
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

	@RequestMapping(value="/create")
	ResponseEntity<?> create(@RequestParam("url") String url, @RequestHeader HttpHeaders headers) {
		headers.toSingleValueMap().each { key, value ->
			logger.info ">> ${key}: ${value}"
		}
		def responseBody = [ success: true,
							 accountIdentifier: "new-account-identifier" ]
		new ResponseEntity<>(responseBody, HttpStatus.CREATED)
	}
}

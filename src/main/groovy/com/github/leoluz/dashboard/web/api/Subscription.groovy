package com.github.leoluz.dashboard.web.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/subscriptions")
class Subscription {

	private static final Logger logger = LoggerFactory.getLogger(Subscription.class)

	@RequestMapping(value = "/create")
	ResponseEntity<?> create(@RequestParam("url") String url,
							 @RequestHeader HttpHeaders headers) {

		def responseBody = [ success: true,
							 accountIdentifier: "new-account-identifier" ]
		new ResponseEntity<>(responseBody, HttpStatus.CREATED)
	}
}

package com.github.leoluz.dashboard.api

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping("/api")
class DashboardApi {

	@RequestMapping(value="/hi/{name}", method=GET)
	String home(@PathVariable String name) {
		"Hello! My name is ${name}"
	}
}

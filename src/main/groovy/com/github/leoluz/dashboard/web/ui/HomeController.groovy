package com.github.leoluz.dashboard.web.ui

import org.springframework.boot.Banner
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController {

	@RequestMapping("/")
	def home() {
		def model = [bootVersion: Banner.package.implementationVersion, groovyVersion: GroovySystem.version]
		new ModelAndView("views/home", model)
	}
}

package com.github.leoluz.dashboard.web.ui

import com.github.leoluz.dashboard.domain.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.Banner
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController {

    @Autowired
    UserService userService

    @RequestMapping("/")
    def home() {
        def model = [bootVersion  : Banner.package.implementationVersion,
                     groovyVersion: GroovySystem.version,
                     users        : userService.users]
        new ModelAndView("views/home", model)
    }
}

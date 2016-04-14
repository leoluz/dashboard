package com.github.leoluz.dashboard.web.ui

import com.github.leoluz.dashboard.domain.service.SubscriptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.Banner
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController {

    @Autowired
    SubscriptionService subscriptionService

    @RequestMapping("/")
    def home() {
        def model = [bootVersion  : Banner.package.implementationVersion,
                     groovyVersion: GroovySystem.version,
                     subscriptions: subscriptionService.subscriptions]
        new ModelAndView("views/home", model)
    }
}

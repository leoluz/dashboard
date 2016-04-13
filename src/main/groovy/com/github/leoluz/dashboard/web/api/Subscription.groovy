package com.github.leoluz.dashboard.web.api

import com.github.leoluz.dashboard.Config
import com.github.leoluz.dashboard.domain.User
import com.github.leoluz.dashboard.domain.service.UserService
import groovy.json.JsonSlurper
import oauth.signpost.OAuthConsumer
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static org.apache.http.HttpHeaders.ACCEPT

@RestController
@RequestMapping("/api/subscriptions")
class Subscription {

    private static final Logger logger = LoggerFactory.getLogger(Subscription.class)

    @Autowired
    UserService userService

    @Autowired
    Config config

    @RequestMapping(value = "/create")
    ResponseEntity<?> create(@RequestParam("url") String url,
                             @RequestHeader HttpHeaders headers) {

        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(config.CONSUMER_KEY, config.CONSUMER_SECRET)
        HttpGet request = new HttpGet(url)
        request.addHeader(ACCEPT, "application/json")
        consumer.sign(request)
        CloseableHttpClient client = HttpClients.createDefault()
        CloseableHttpResponse response = client.execute(request)
        def json
        if (response.statusLine.statusCode == 200) {
            def slurper = new JsonSlurper()
            json = slurper.parse(response.getEntity().getContent())
        }
        client.close()
        response.close()

        User user = userService.create(json?.creator?.email, json?.payload?.order?.editionCode)

        //TODO
        def responseBody = [success          : true,
                            accountIdentifier: user.id]
        new ResponseEntity<>(responseBody, HttpStatus.CREATED)
    }

    @RequestMapping(value = "/change")
    ResponseEntity<?> change(@RequestParam("url") String url) {

    }
}

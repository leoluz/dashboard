package com.github.leoluz.dashboard.web.api

import com.github.leoluz.dashboard.Config
import com.github.leoluz.dashboard.domain.User
import com.github.leoluz.dashboard.domain.service.UserConflictException
import com.github.leoluz.dashboard.domain.service.UserNotFoundException
import com.github.leoluz.dashboard.domain.service.UserService
import com.github.leoluz.dashboard.infra.http.OauthKeys
import com.github.leoluz.dashboard.infra.http.Response
import com.github.leoluz.dashboard.infra.http.RestClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.HttpStatus.CONFLICT
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

@RestController
@RequestMapping("/api/subscriptions")
class Subscription {

    private static final Logger logger = LoggerFactory.getLogger(Subscription.class)

    @Autowired
    UserService userService

    @Autowired
    RestClient client

    @Autowired
    Config config

    @RequestMapping(value = "/create")
    ResponseEntity<?> create(@RequestParam("url") String url,
                             @RequestHeader HttpHeaders headers) {

        Response response = client.get(url, buildOauthKeys())
        def responseBody, status


        if (response.status == 200) {
            try {
                User user = userService.
                        create(response?.body?.creator?.email, response?.body?.payload?.order?.editionCode)

                responseBody = [success          : true,
                                accountIdentifier: user.id]
                status = CREATED
            } catch (UserConflictException e) {
                responseBody = [success: false,
                                errorCode: "USER_ALREADY_EXISTS"]
                status = CONFLICT
            }
        } else {
            responseBody = [success: false,
                            errorCode: "UNKNOWN_ERROR",
                            message: "Http error: ${response.status}"]
            status = INTERNAL_SERVER_ERROR
        }
        new ResponseEntity<>(responseBody, status)
    }

    @RequestMapping(value = "/change")
    ResponseEntity<?> change(@RequestParam("url") String url) {

        Response response = client.get(url, buildOauthKeys())
        def responseBody, status

        if (response.status == 200) {
            try {
                userService.update(buildUser(response.body))
                responseBody = [success: true]
                status = OK
            } catch (UserNotFoundException exception) {
                responseBody = [success: false,
                                errorCode: "USER_NOT_FOUND"]
                status = NOT_FOUND
            }
        } else {
            responseBody = [success: false,
                            errorCode: "UNKNOWN_ERROR",
                            message: "Http error: ${response.status}"]
            status = INTERNAL_SERVER_ERROR
        }

        new ResponseEntity<>(responseBody, status)

    }

    def buildUser(responseBody) {
        def id = responseBody?.payload?.account?.accountIdentifier
        def email = responseBody?.creator?.email
        def edition = responseBody?.payload?.order?.editionCode
        new User(id, email, edition)
    }

    def buildOauthKeys() {
        OauthKeys keys = new OauthKeys()
        keys.with {
            consumerKey = config.CONSUMER_KEY
            consumerSecret = config.CONSUMER_SECRET
        }
        keys
    }
}

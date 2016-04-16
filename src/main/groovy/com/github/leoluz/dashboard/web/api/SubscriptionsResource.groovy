package com.github.leoluz.dashboard.web.api

import com.github.leoluz.dashboard.Config
import com.github.leoluz.dashboard.domain.Subscription

import com.github.leoluz.dashboard.domain.service.SubscriptionNotFoundException
import com.github.leoluz.dashboard.domain.service.SubscriptionService
import com.github.leoluz.dashboard.infra.http.OauthKeys
import com.github.leoluz.dashboard.infra.http.Response
import com.github.leoluz.dashboard.infra.http.RestClient
import groovy.json.JsonBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.HttpStatus.OK

@RestController
@RequestMapping("/api/subscriptions")
class SubscriptionsResource {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionsResource.class)

    @Autowired
    SubscriptionService subscriptionService

    @Autowired
    RestClient client

    @Autowired
    Config config

    @RequestMapping(value = "/create")
    ResponseEntity<?> create(@RequestParam("url") String url,
                             @RequestHeader HttpHeaders headers) {
        Response response = client.get(url, buildOauthKeys())
        logger.info("Create event:\n ${(new JsonBuilder(response.body)).toPrettyString()}")

        def responseBody
        if (response.status == 200) {
            Subscription subscription = subscriptionService.
                    create(response?.body?.payload?.order?.editionCode)
            subscriptionService.addUser(subscription.id, response?.body?.creator?.email)

            responseBody = [success          : true,
                            accountIdentifier: subscription.id]
        } else {
            responseBody = [success: false,
                            errorCode: "UNKNOWN_ERROR"]
        }
        new ResponseEntity<>(responseBody, OK)
    }

    @RequestMapping(value = "/change")
    ResponseEntity<?> change(@RequestParam("url") String url) {
        Response response = client.get(url, buildOauthKeys())
        logger.info("Change event:\n ${(new JsonBuilder(response.body)).toPrettyString()}")

        def responseBody
        if (response.status == 200) {
            try {
                subscriptionService.update(buildSubscription(response.body))
                responseBody = [success: true]
            } catch (SubscriptionNotFoundException exception) {
                responseBody = [success: false,
                                errorCode: "USER_NOT_FOUND"]
            }
        } else {
            responseBody = [success: false,
                            errorCode: "UNKNOWN_ERROR"]
        }
        new ResponseEntity<>(responseBody, OK)
    }

    @RequestMapping(value = "/cancel")
    ResponseEntity<?> cancel(@RequestParam("url") String url) {
        Response response = client.get(url, buildOauthKeys())
        logger.info("Cancel event:\n ${(new JsonBuilder(response.body)).toPrettyString()}")

        def responseBody
        if (response.status == 200) {
            subscriptionService.delete(getSubscriptionId(response))
            responseBody = [success: true]
        } else {
            responseBody = [success: false,
                            errorCode: "UNKNOWN_ERROR"]
        }
        new ResponseEntity<>(responseBody, OK)
    }

    @RequestMapping(value = "/assign")
    ResponseEntity<?> assignUser(@RequestParam("url") String url) {
        Response response = client.get(url, buildOauthKeys())
        logger.info("Assign event:\n ${(new JsonBuilder(response.body)).toPrettyString()}")

        def responseBody
        if (response.status == 200) {
            subscriptionService.addUser(getSubscriptionId(response), getUserEmail(response))
            responseBody = [success: true]
        } else {
            responseBody = [success: false,
                            errorCode: "UNKNOWN_ERROR"]
        }
        new ResponseEntity<>(responseBody, OK)
    }

    @RequestMapping(value = "/unassign")
    ResponseEntity<?> unassignUser(@RequestParam("url") String url) {
        Response response = client.get(url, buildOauthKeys())
        logger.info("Unassign event:\n ${(new JsonBuilder(response.body)).toPrettyString()}")

        def responseBody
        if (response.status == 200) {
            subscriptionService.removeUser(getSubscriptionId(response), getUserEmail(response))
            responseBody = [success: true]
        } else {
            responseBody = [success: false,
                            errorCode: "UNKNOWN_ERROR"]
        }
        new ResponseEntity<>(responseBody, OK)
    }

    def buildSubscription(responseBody) {
        def id = responseBody?.payload?.account?.accountIdentifier
        def email = responseBody?.creator?.email
        def edition = responseBody?.payload?.order?.editionCode
        new Subscription(id, email, edition)
    }

    def buildOauthKeys() {
        OauthKeys keys = new OauthKeys()
        keys.with {
            consumerKey = config.CONSUMER_KEY
            consumerSecret = config.CONSUMER_SECRET
        }
        keys
    }

    def getSubscriptionId(response) {
        response?.body?.payload?.account?.accountIdentifier
    }

    def getUserEmail(response) {
        response?.body?.payload?.user?.email
    }
}

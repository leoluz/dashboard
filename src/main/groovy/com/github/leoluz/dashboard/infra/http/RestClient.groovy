package com.github.leoluz.dashboard.infra.http

import groovy.json.JsonSlurper
import oauth.signpost.OAuthConsumer
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.springframework.stereotype.Component

import static org.apache.http.HttpHeaders.ACCEPT

@Component
class RestClient {

    CloseableHttpClient client

    public RestClient() {
        this.client = HttpClients.createDefault()
    }

    def get(String url, OauthKeys keys = null) {

        Response response = new Response()
        HttpGet request = new HttpGet(url)

        request.addHeader(ACCEPT, "application/json")
        if (keys) {
            sign(request, keys)
        }

        CloseableHttpResponse httpResponse = client.execute(request)
        response.status = httpResponse.statusLine.statusCode
        response.headers = parseHeaders(httpResponse.getAllHeaders())
        if (response.status == 200) {
            response.body = parseEntity(httpResponse.getEntity())
        }

        httpResponse.close()
        response
    }

    def parseHeaders(headers) {
        headers?.collectEntries {
            [(it.getName()): it.getValue()]
        }
    }
    def parseEntity(entity) {
        if (entity) {
            def slurper = new JsonSlurper()
            slurper.parse(entity.getContent())
        }
    }

    def sign(request, keys) {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(keys.consumerKey, keys.consumerSecret)
        consumer.sign(request)
    }
}

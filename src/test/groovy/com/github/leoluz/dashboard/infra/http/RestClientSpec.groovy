package com.github.leoluz.dashboard.infra.http

import spock.lang.Specification

class RestClientSpec extends Specification {

    RestClient client

    def setup() {
        client = new RestClient()
    }

    def "will receive valid response from a GET"() {
        given:
        def url = "https://www.appdirect.com/api/integration/v1/events/dummyOrder"

        when:
        Response response = client.get(url)

        then:
        response.status == 200
        response.body.type == "SUBSCRIPTION_ORDER"
    }
}

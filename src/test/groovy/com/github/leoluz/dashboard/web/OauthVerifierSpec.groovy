package com.github.leoluz.dashboard.web

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

import static org.springframework.http.HttpHeaders.AUTHORIZATION

class OauthVerifierSpec extends Specification {

	OauthVerifier oauthVerifier
	HttpServletRequest request

	def setup() {
		request = Mock(HttpServletRequest)
		oauthVerifier = new OauthVerifier(request, "SECRET")
	}

	def "will get valid signature"() {
		given:
		StringBuffer url = new StringBuffer("https://appdirect-dashboard.herokuapp.com/api/subscriptions/create")
		request.getMethod() >> "GET"
		request.getHeader(AUTHORIZATION) >> 'OAuth realm="", oauth_version="1.0", oauth_consumer_key="appdirectdashboard-102404", oauth_timestamp="1460239382", oauth_nonce="791448561331883136", oauth_signature_method="HMAC-SHA1", oauth_signature="wTOafLvDrj4XSM0rqI%2BI7JqfPes%3D"'
		request.getParameterMap() >> [url: ["https%3A%2F%2Fwww.appdirect.com%2Fapi%2Fintegration%2Fv1%2Fevents%2FdummyOrder"]]
		request.getRequestURL() >> url

		when:
		def hasValidSignature = oauthVerifier.hasValidSignature()

		then:
		hasValidSignature
	}

	def "will compute signature correctly"() {
		given:
		def httpMethod = "GET"
		def baseUrl = "https://appdirect-dashboard.herokuapp.com/api/subscriptions/create"
		def reqParms = [url: "https%3A%2F%2Fwww.appdirect.com%2Fapi%2Fintegration%2Fv1%2Fevents%2FdummyOrder",
						oauth_consumer_key: "appdirectdashboard-102404",
						oauth_nonce: "791448561331883136",
						oauth_signature_method: "HMAC-SHA1",
						oauth_timestamp: "1460239382",
						oauth_version: "1.0"]

		when:
		def signature = oauthVerifier.computeSignature(httpMethod, baseUrl, reqParms)

		then:
		"wTOafLvDrj4XSM0rqI+I7JqfPes=" == signature
	}

	def "will build valid oauth params map"() {
		given:
		def authHeader = 'OAuth realm="",oauth_version="1.0",oauth_consumer_key="appdirectdashboard-102404",oauth_timestamp="1460239382",oauth_nonce="791448561331883136",oauth_signature_method="HMAC-SHA1",oauth_signature="GJQ8gSWiGirLV3gFmmwYWywUrYQ%3D"'

		when:
		def oauthParams = oauthVerifier.buildOauthParams(authHeader)

		then:
		6 == oauthParams.size()
		'GJQ8gSWiGirLV3gFmmwYWywUrYQ%3D' == oauthParams.oauth_signature
		'appdirectdashboard-102404' == oauthParams.oauth_consumer_key

	}

	def "will build valid request params map"() {
		given:
		def queryMap = [url: "https%3A%2F%2Fwww.appdirect.com%2Fapi%2Fintegration%2Fv1%2Fevents%2FdummyOrder"]
		def oauthParams = [oauth_consumer_key: "appdirectdashboard-102404",
						   oauth_nonce: "791448561331883136",
						   oauth_signature: "GJQ8gSWiGirLV3gFmmwYWywUrYQ%3D",
						   oauth_signature_method: "HMAC-SHA1",
						   oauth_timestamp: "1460239382",
						   oauth_version: "1.0"]

		when:
		def requestParams = oauthVerifier.buildRequestParams(queryMap, oauthParams)

		then:
		6 == requestParams.size()
	}
}

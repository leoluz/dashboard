package com.github.leoluz.dashboard.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sun.misc.BASE64Encoder

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.HttpServletRequest

import static org.springframework.http.HttpHeaders.AUTHORIZATION

class OauthVerifier {

	private final String HMAC_SHA1 = "HmacSHA1"
	private final String consumerSecret
	private final HttpServletRequest request

	private static final Logger logger = LoggerFactory.getLogger(OauthVerifier.class)

	OauthVerifier(HttpServletRequest request, String consumerSecret) {
		this.request = request
		this.consumerSecret = consumerSecret
	}

	boolean hasValidSignature() {
		def authHeader = request.getHeader(AUTHORIZATION)

		logger.info(">>> ${authHeader}")

		if (authHeader) {
			def oauthParams = buildOauthParams(authHeader)
			def queryParamsMap = buildQueryParamsMap(request.getParameterMap())
			def requestParams = buildRequestParams(queryParamsMap, oauthParams)
			def computedSignature = computeSignature(request.getMethod(), request.getRequestURL().toString(), requestParams)
			computedSignature == decode(oauthParams.oauth_signature)
		} else {
			false
		}
	}

	def buildQueryParamsMap(queryParams) {
		def queryParamsMap = [:]
		queryParams.each { key, value ->
			queryParamsMap << [(key): value[0]]
		}
		queryParamsMap
	}

	def buildRequestParams(queryParamsMap, oauthParams) {
		def keys = ["oauth_consumer_key",
					"oauth_nonce",
					"oauth_signature_method",
					"oauth_timestamp",
					"oauth_version"]
		def requestParams = oauthParams?.subMap(keys)
		queryParamsMap?.each {
			requestParams << it
		}
		requestParams
	}

	def buildOauthParams(authHeader) {

		def authList = authHeader.split(" ")
		if (authList.size() == 2) {
			def split = authList[1].split(",")
			def requestMap = [:]
			split.each {
				def entry = it.split("=")
				requestMap << [(entry[0]): entry[1].replaceAll(/^"|"$/, "")]
			}
			requestMap.findAll { it =~ /^oauth_.+/ }
		} else {
			null
		}
	}

	String computeSignature(httpMethod, baseUrl, requestParams) {

		def paramString = requestParams.sort().collect { key, value ->
			"${key}=${value}"
		}.join("&")

		def signBaseString = "${httpMethod}&${encode(baseUrl)}&${encode(paramString)}"
		SecretKeySpec keySpec = new SecretKeySpec("${consumerSecret}&".getBytes(), HMAC_SHA1)
		Mac mac = Mac.getInstance(HMAC_SHA1)
		mac.init(keySpec)
		byte[] result = mac.doFinal(signBaseString.getBytes())
		BASE64Encoder encoder = new BASE64Encoder();
		encoder.encode(result)
	}

	def encode(string) {
		URLEncoder.encode(string, "UTF-8")
	}

	def decode(string) {
		URLDecoder.decode(string, "UTF-8")
	}
}

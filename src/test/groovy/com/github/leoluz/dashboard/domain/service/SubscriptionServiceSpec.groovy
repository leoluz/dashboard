package com.github.leoluz.dashboard.domain.service

import com.github.leoluz.dashboard.domain.Subscription
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class SubscriptionServiceSpec extends Specification {

	@Shared
	SubscriptionService subscriptionService

	@Shared
	def subscriptionId

	def setupSpec() {
		subscriptionService = new SubscriptionService()
	}

	def "will create a new subscription"() {
		when:
		Subscription subscription = subscriptionService.create("someEmail", "free")
		subscriptionId = subscription.id

		then:
		subscription != null
	}

	def "will return the correct number of subscriptions"() {
		when:
		subscriptionService.create("anotherEmail", "free")

		then:
		subscriptionService.subscriptions.size() == 2
	}

	def "will update existing subscription"() {
		given:
		Subscription subscription = new Subscription(subscriptionId, "updateEmail", "paid")

		when:
		Subscription updatedSubscription = subscriptionService.update(subscription)

		then:
		updatedSubscription.email == "updateEmail"
		updatedSubscription.edition == "paid"
		subscriptionService.subscriptions.size() == 2
	}

	def "will raise error when updating non existing subscription"() {
		given:
		Subscription subscription = new Subscription("nonexisting", "free")

		when:
		subscriptionService.update(subscription)

		then:
		thrown SubscriptionNotFoundException
	}
}

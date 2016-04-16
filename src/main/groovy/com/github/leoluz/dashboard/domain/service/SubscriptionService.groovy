package com.github.leoluz.dashboard.domain.service

import com.github.leoluz.dashboard.domain.Subscription
import groovy.transform.PackageScope
import groovy.transform.Synchronized
import org.springframework.stereotype.Component

@Component
class SubscriptionService {

    @PackageScope
    Set<Subscription> subscriptions = Collections.synchronizedSet([] as Set)

    @Synchronized
    def create(edition) {
        Subscription subscription = new Subscription(edition)
        subscriptions << subscription
        subscription
    }

    @Synchronized
    def update(Subscription subscription) {
        if (subscription in subscriptions) {
            Subscription updateSubscription = subscriptions.find { it.id == subscription.id }
            updateSubscription.edition = subscription.edition
            updateSubscription
        } else {
            throw new SubscriptionNotFoundException("Subscription update error: Subscription not found!")
        }

    }

    def delete(subscriptionId) {
        subscriptions.removeAll {it.id == subscriptionId}
    }

    @Synchronized
    def addUser(subscriptionId, email) {
        def subscription = subscriptions.find { it.id = subscriptionId }
        subscription.users << email
        true
    }

    @Synchronized
    def removeUser(subscriptionId, email) {
        def subscription = subscriptions.find { it.id = subscriptionId }
        subscription.users.remove(email)
        true
    }
}

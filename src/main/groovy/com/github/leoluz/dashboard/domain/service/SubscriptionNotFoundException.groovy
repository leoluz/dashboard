package com.github.leoluz.dashboard.domain.service

class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(String message) {
        super(message)
    }
}

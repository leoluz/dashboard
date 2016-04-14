package com.github.leoluz.dashboard.domain

class Subscription {
    String id, email, edition

    public Subscription(id = null, email, edition) {
        if (id) {
            this.id = id
        } else {
            this.id = UUID.randomUUID().toString()
        }
        this.email = email
        this.edition = edition
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Subscription subscription = (Subscription) o

        if (id != subscription.id) return false

        return true
    }

    int hashCode() {
        return (id != null ? id.hashCode() : 0)
    }
}

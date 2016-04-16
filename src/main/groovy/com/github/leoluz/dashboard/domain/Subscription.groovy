package com.github.leoluz.dashboard.domain

class Subscription {
    String id, edition
    Set<User> users

    public Subscription(id = null, edition) {
        if (id) {
            this.id = id
        } else {
            this.id = UUID.randomUUID().toString()
        }
        this.edition = edition
        this.users = [] as Set
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

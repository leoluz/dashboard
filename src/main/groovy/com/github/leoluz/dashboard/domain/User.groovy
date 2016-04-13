package com.github.leoluz.dashboard.domain

class User {
    String id, email, edition

    public User(id = null, email, edition) {
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

        User user = (User) o

        if (id != user.id) return false

        return true
    }

    int hashCode() {
        return (id != null ? id.hashCode() : 0)
    }
}

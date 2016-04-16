package com.github.leoluz.dashboard.domain

class User {
    def name, email

    public User(name, email) {
        this.name = name
        this.email = email
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        User user = (User) o

        if (email != user.email) return false
        if (name != user.name) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + email.hashCode()
        return result
    }
}

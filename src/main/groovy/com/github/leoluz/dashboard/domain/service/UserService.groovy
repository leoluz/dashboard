package com.github.leoluz.dashboard.domain.service

import com.github.leoluz.dashboard.domain.User
import groovy.transform.PackageScope
import groovy.transform.Synchronized
import org.springframework.stereotype.Component

@Component
class UserService {

	@PackageScope
	Set<User> users = Collections.synchronizedSet([] as Set)

	@Synchronized
	def create(email, edition) {
		def userFound = users.find { it.email == email }
		if (userFound) {
			throw new UserConflictException("User creation error: Email already used!")
		} else {
			User user = new User(email, edition)
			users << user
			user
		}
	}

	@Synchronized
	def update(User user) {
		if (user in users) {
			User updateUser = users.find {it.id == user.id}
			updateUser.email = user.email
			updateUser.edition = user.edition
			updateUser
		} else {
			throw new UserNotFoundException("User update error: User not found!")
		}

	}
}

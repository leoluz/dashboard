package com.github.leoluz.dashboard.domain.service

import com.github.leoluz.dashboard.domain.User
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class UserServiceSpec extends Specification {

	@Shared
	UserService userService

	@Shared
	def userId

	def setupSpec() {
		userService = new UserService()
	}

	def "will create a new user"() {
		when:
		userId = userService.create("someEmail", "free")

		then:
		userId != null
	}

	def "will raise error when try to create with same email"() {
		when:
		userService.create("someEmail", "free")

		then:
		thrown UserConflictException
	}

	def "will return the correct number of users"() {
		when:
		userService.create("anotherEmail", "free")

		then:
		userService.users.size() == 2
	}

	def "will update existing user"() {
		given:
		User user = new User(userId, "updateEmail", "paid")

		when:
		User updatedUser = userService.update(user)

		then:
		updatedUser.email == "updateEmail"
		updatedUser.edition == "paid"
		userService.users.size() == 2
	}

	def "will raise error when updating non existing user"() {
		given:
		User user = new User("nonexistinguser", "free")

		when:
		userService.update(user)

		then:
		thrown UserNotFoundException
	}
}

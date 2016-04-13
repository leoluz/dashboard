package com.github.leoluz.dashboard.domain.service

class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message) {
		super(message)
	}
}

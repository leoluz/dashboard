package com.github.leoluz.dashboard

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Config {

	@Value('${CONSUMER_SECRET}')
	public String CONSUMER_SECRET
}

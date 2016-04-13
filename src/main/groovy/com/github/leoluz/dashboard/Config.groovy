package com.github.leoluz.dashboard

import groovy.transform.Immutable
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Immutable
class Config {

    @Value('${CONSUMER_KEY}')
    String CONSUMER_KEY

    @Value('${CONSUMER_SECRET}')
    String CONSUMER_SECRET
}

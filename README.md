AppDirect Dashboard
===================

This project integrates with the following AppDirect events:

* Subscribe to your product.
* Assign (add) a user to your product.
* Un-assign (remove) users from your product.
* Change (update) the subscription.
* Cancel the subscription.

For all the events above it will check the oauth signature validity first. 
In case the signature is not valid it will return 401 Unauthorized error code.

This project is deployed in [Heroku][5] under the following url:

* https://appdirect-dashboard.herokuapp.com/

Technologies overview:
--------------

This project was built with the following technologies:

* [Spring Boot][1]
* [Groovy][2]
* [Gradle][3]
* [Groovy Template Engine][4]
* [Spock][6]

Pre-reqs:
--------

To build this project all you need is the java8 sdk installed.

Generating the war file:
----------

1. Clone this repo.

    `$ git clone git@github.com:leoluz/java-web-template.git`

2. At project root directory run:

    `$ ./gradlew build`

A war file will be generated at `<project-home>/build/lib` directory.

Running:
--------

1. At project root directory run:

    `$ ./gradlew bootRun`
    
2. After starting the server just open in the browser the following url:

    `http://localhost:8080`

3. You can play with the application's api importing the following Postman collection:

    `https://www.getpostman.com/collections/97c100b2d8109bb9d138`

[1]: http://projects.spring.io/spring-boot/      "SpringBoot"
[2]: http://www.groovy-lang.org/                 "Groovy Lang"
[3]: http://gradle.org/                          "Gradle"
[4]: http://docs.groovy-lang.org/docs/next/html/documentation/template-engines.html#_the_markuptemplateengine "Groovy Template"                         "Gradle"
[5]: https://www.heroku.com/home                 "Heroku"
[6]: http://spockframework.github.io/spock/docs/1.0/index.html "Spock"

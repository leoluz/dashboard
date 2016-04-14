layout 'layouts/main.tpl',
        pageTitle: 'Spring Boot - Groovy templates example with layout',
        mainBody: contents {
            div("This is an application using Boot $bootVersion and Groovy templates $groovyVersion")
            p("List of subscriptions:")
            ul {
                subscriptions.each { subscription ->
                    li {
                        yield "${subscription.id}: ${subscription.email} (${subscription.edition})"
                    }
                }
            }
        }

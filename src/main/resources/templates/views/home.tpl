layout 'layouts/main.tpl',
        pageTitle: 'Spring Boot - Groovy templates example with layout',
        mainBody: contents {
            div("This is an application using Boot $bootVersion and Groovy templates $groovyVersion")
            p("List of users:")
            ul {
                users.each { user ->
                    li {
                        yield "${user.id}: ${user.email} (${user.edition})"
                    }
                }
            }
        }

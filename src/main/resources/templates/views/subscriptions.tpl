h3("Subscriptions")
table(class: 'table table-striped') {
    thead {
        tr {
            th("Id")
            th("Edition")
            th("Users")
        }
    }
    tbody {
        spring.model.subscriptions.each { subscription ->
            tr {
                th("${subscription.id}")
                th("${subscription.edition}")
                th {
                    subscription.users.each { user ->
                        p(user)
                    }
                }
            }
        }
    }
}

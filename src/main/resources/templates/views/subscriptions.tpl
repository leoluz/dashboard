h3("Subscriptions")
table(class: 'table table-striped') {
    thead {
        tr {
            th("Id")
            th("Email")
            th("Edition")
        }
    }
    tbody {
        spring.model.subscriptions.each { subscription ->
            tr {
                th("${subscription.id}")
                th("${subscription.email}")
                th("${subscription.edition}")
            }
        }
    }
}

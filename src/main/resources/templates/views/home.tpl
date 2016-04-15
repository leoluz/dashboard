layout 'layouts/main.tpl', true,
        pageTitle: 'Subscriptions',
        mainBody: contents {
            div(class: 'container') {
                div(class: 'navbar') {
                    div(class: 'navbar-inner') {
                        h1('AppDirect Dashboard')
                    }
                }
                include template: 'views/subscriptions.tpl'
            }
            include template: 'views/footer.tpl'
        }

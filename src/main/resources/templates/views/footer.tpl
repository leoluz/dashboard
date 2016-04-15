footer(class: 'footer') {
    div(class: 'container') {
        p(class: 'text-muted') {
            yield "This is an application using Boot ${bootVersion} and Groovy templates ${groovyVersion}"
        }
    }
}

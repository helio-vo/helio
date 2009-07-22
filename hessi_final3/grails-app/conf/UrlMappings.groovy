class UrlMappings {
    static mappings = {
        "/"(controller: "flare")
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }
        "500"(view: '/error')
    }
}

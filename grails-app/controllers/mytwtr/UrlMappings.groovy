package mytwtr

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/accounts"(resources: 'account') {
            "/messages"(resources: 'message')
        }

        "/accounts/${id}/follow/${followAccountId}"(controller: "account", action: "follow", method: "POST")
        "/accounts/${id}/unfollow/${unfollowAccountId}"(controller: "account", action: "unfollow", method: "POST")
        "/accounts/${id}/postMessage"(controller: "message", action: "postMessage", method: "POST")

        "/"(view: "/index")
        "500"(view: '/error')
        "404"(view: '/notFound')
    }

}

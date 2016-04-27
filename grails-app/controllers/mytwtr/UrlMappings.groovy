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
        "/accounts/${id}/getfollowers"(controller: "account", action: "getFollowers", method: "GET")
        "/accounts/${id}/shownewsfeed"(controller: "account", action: "shownewsfeed", method: "GET")
        "/messages/${id}/recentmessages"(controller: "message", action: "recentMessages", method: "GET")
        "/messages/search"(controller: "message", action: "search", method: "GET")

        "/"(view: "/index")
        //"500"(view: '/error')
        //"404"(view: '/notFound')

        "500"(controller: 'Error', action: 'internalServerError')
        "404"(controller: 'Error', action: 'notFound')
        "401"(controller: 'Error', action: 'unauthorized')
        "403"(controller: 'Error', action: 'forbidden')
    }

}

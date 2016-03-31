package mytwtr

import grails.converters.JSON

class ErrorController {

    def internalServerError() {
        response.status = 500
/*        render(contentType: 'application/json') {
            error = response.status
            message = 'Internal server error'
        }*/
        render ([error: response.status, message: "Internal server error"] as JSON)
    }

    def notFound() {
/*        response.status = 404
        render(contentType: 'application/json') {
            error = response.status
            message = 'Not found'
        }*/
        render ([error: response.status, message: "Not foun"] as JSON)
    }

    def unauthorized() {
        response.status = 401
/*        render(contentType: 'application/json') {
            error = response.status
            message = 'Unauthorized'
        }*/
        render ([error: response.status, message: "Unauthorized"] as JSON)
    }

    def forbidden() {
        response.status = 403
/*        render(contentType: 'application/json') {
            error = response.status
            message = 'Forbidden'
        }*/
        render ([error: response.status, message: "Forbidden"] as JSON)
    }
}

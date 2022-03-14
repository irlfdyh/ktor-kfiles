package com.plygrnd.ktor.kfiles.plugins

import com.plygrnd.ktor.kfiles.route.basicFileRoute
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        basicFileRoute()
    }
}

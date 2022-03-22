package com.plygrnd.ktor.kfiles.route

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.rootFileRoute() {
    routing {
        static("custom") {
            staticRootFolder = File("/system")
            files("public")
        }
        route("/root") {
            get {
                call.respondFile(File("/system/public/low-quality-image.jpg"))
            }
        }
    }
}
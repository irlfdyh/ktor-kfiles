package com.plygrnd.ktor.kfiles

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.plygrnd.ktor.kfiles.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}

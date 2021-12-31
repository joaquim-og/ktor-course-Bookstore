package com.confrades

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.confrades.plugins.*
import io.ktor.application.*
import io.ktor.auth.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureStatusPages()
        configureCallLogging()
        configureSecurity()
        configureLocations()
        configureRouting()
        configureTemplating()
        configureHTTP()
        configureSerialization()
    }.start(wait = true)
}

package com.confrades

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.confrades.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureTemplating()
        configureMonitoring()
        configureHTTP()
        configureSerialization()
    }.start(wait = true)
}

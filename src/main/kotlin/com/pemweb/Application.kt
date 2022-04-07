package com.pemweb

import com.pemweb.di.databaseModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.pemweb.plugins.*
import io.ktor.application.*
import org.koin.core.logger.Level
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun main() {
	embeddedServer(Netty, port = 8080, host = "localhost") {
		install(Koin) {
			slf4jLogger(Level.ERROR)
			modules(listOf(databaseModule))
		}
		configureRouting()
		configureSerialization()
	}.start(wait = true)
}

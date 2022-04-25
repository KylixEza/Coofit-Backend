package com.pemweb.app

import com.pemweb.di.controllerModule
import com.pemweb.di.databaseModule
import com.pemweb.di.repositoryModule
import com.pemweb.di.routeModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.util.*
import org.koin.core.logger.Level
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

@InternalAPI
@KtorExperimentalLocationsAPI
fun main() {
	embeddedServer(Netty, port = System.getenv("PORT").toInt(), host = "localhost") {
		install(Koin) {
			slf4jLogger(Level.ERROR)
			modules(listOf(databaseModule, repositoryModule, controllerModule, routeModule))
		}
		install(Locations)
		configureRouting()
		configureSerialization()
	}.start(wait = true)
}

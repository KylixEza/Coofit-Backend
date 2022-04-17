package com.pemweb.app

import com.pemweb.route.menu.MenuRoute
import com.pemweb.route.user.UserRoute
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.inject

@KtorExperimentalLocationsAPI
fun Application.configureRouting() {
	
	val menuRoute by inject<MenuRoute>()
	val userRoute by inject<UserRoute>()
	
	routing {
		get("/") {
			call.respondText("Hello World!")
		}
		menuRoute.apply { initRoute() }
		userRoute.apply { initRoute() }
	}
}

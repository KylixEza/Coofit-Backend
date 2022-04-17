package com.pemweb.di

import com.pemweb.route.menu.MenuRoute
import io.ktor.locations.*
import org.koin.dsl.module

@KtorExperimentalLocationsAPI
val routeModule = module {
	factory { MenuRoute(get()) }
}
package com.pemweb.di

import com.pemweb.controller.IMenuController
import com.pemweb.controller.IUserController
import com.pemweb.controller.MenuController
import com.pemweb.controller.UserController
import org.koin.dsl.module

val controllerModule = module {
	single<IUserController> {
		UserController(get())
	}
	single<IMenuController> {
		MenuController(get())
	}
}
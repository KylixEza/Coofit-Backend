package com.pemweb.route.menu

import com.pemweb.controller.IMenuController
import com.pemweb.helper.ResponseModelHelper.generalException
import com.pemweb.model.menu.MenuBody
import com.pemweb.model.prediction.PredictionBody
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*

import io.ktor.locations.post
import io.ktor.request.*

@KtorExperimentalLocationsAPI
class MenuRoute(
	private val controller: IMenuController
) {
	private fun Route.postMenu() {
		post<MenuRouteLocation.MenuPostRoute> {
			val body = try {
				call.receive<MenuBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			controller.apply { call.addNewMenu(body) }
		}
	}
	
	private fun Route.getTopMenu() {
		get<MenuRouteLocation.MenuTopGetRoute> {
			controller.apply { call.getSomeMenus() }
		}
	}
	
	private fun Route.getMenuDetail() {
		get<MenuRouteLocation.MenuDetailGetRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			controller.apply { menuId?.let { menuId -> call.getMenuDetail(menuId) } }
		}
	}
	
	private fun Route.getMenuCaloriesPrediction() {
		get<MenuRouteLocation.MenuCaloriesPredictionGetRoute> {
			val body = try {
				call.receive<PredictionBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			
			controller.apply { call.getCaloriesPrediction(body) }
		}
	}
	
	fun Route.initRoute() {
		this.apply {
			postMenu()
			getTopMenu()
			getMenuDetail()
			getMenuCaloriesPrediction()
		}
	}
}
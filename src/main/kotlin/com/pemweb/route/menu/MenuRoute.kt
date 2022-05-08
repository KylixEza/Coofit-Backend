package com.pemweb.route.menu

import com.oreyo.model.ingredient.IngredientBody
import com.oreyo.model.review.ReviewBody
import com.oreyo.model.step.StepBody
import com.pemweb.controller.IMenuController
import com.pemweb.helper.ResponseModelHelper.generalException
import com.pemweb.model.menu.MenuBody
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
	
	private fun Route.postIngredient() {
		post<MenuRouteLocation.MenuPostIngredientRoute> {
			
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			} ?: ""
			
			val body = try {
				call.receive<IngredientBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			controller.apply { call.addNewIngredient(menuId, body) }
		}
	}
	
	private fun Route.postStep() {
		post<MenuRouteLocation.MenuPostStepRoute> {
			
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			} ?: ""
			
			val body = try {
				call.receive<StepBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			controller.apply { call.addNewStep(menuId, body) }
		}
	}
	
	private fun Route.postReview() {
		post<MenuRouteLocation.MenuPostReviewRoute> {
			
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			} ?: ""
			
			val body = try {
				call.receive<ReviewBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			controller.apply { call.addNewReview(menuId, body) }
		}
	}
	
	private fun Route.getTopMenu() {
		get<MenuRouteLocation.MenuTopGetRoute> {
			controller.apply { call.getSomeMenus() }
		}
	}
	
	private fun Route.getAllMenus() {
		get<MenuRouteLocation.MenuAllGetRoute> {
			val query = try {
				call.request.queryParameters["query"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			
			if (query != null)
				controller.apply { call.searchMenu(query) }
			else
				controller.apply { call.getAllMenus() }
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
			val food = try {
				call.request.queryParameters["food"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			} ?: ""
			
			controller.apply { call.getCaloriesPrediction(food) }
		}
	}
	
	fun Route.initRoute() {
		this.apply {
			postMenu()
			postIngredient()
			postReview()
			postStep()
			getTopMenu()
			getAllMenus()
			getMenuDetail()
			getMenuCaloriesPrediction()
		}
	}
}
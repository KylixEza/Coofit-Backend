package com.pemweb.route.menu

import com.pemweb.route.BASE_MENU
import com.pemweb.route.BASE_SELECTED_MENU
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
sealed class MenuRouteLocation {
	companion object {
		//POST
		const val POST_MENU = BASE_MENU
		//POST
		const val POST_INGREDIENT = "$BASE_SELECTED_MENU/ingredient"
		//POST
		const val POST_STEP = "$BASE_SELECTED_MENU/step"
		//POST
		const val POST_REVIEW = "$BASE_SELECTED_MENU/review"
		//GET
		const val GET_TOP_MENUS = "$BASE_MENU/top"
		//GET
		const val GET_ALL_MENUS = BASE_MENU
		//GET
		const val GET_MENU_DETAIL = BASE_SELECTED_MENU
		//GET
		const val GET_CALORIES_PREDICTION = "$BASE_MENU/prediction"
	}
	
	@Location(POST_MENU)
	class MenuPostRoute
	
	@Location(POST_INGREDIENT)
	class MenuPostIngredientRoute(val menuId: String)
	
	@Location(POST_STEP)
	class MenuPostStepRoute(val menuId: String)
	
	@Location(POST_REVIEW)
	class MenuPostReviewRoute(val menuId: String)
	
	@Location(GET_TOP_MENUS)
	class MenuTopGetRoute
	
	@Location(GET_ALL_MENUS)
	class MenuAllGetRoute
	
	@Location(GET_MENU_DETAIL)
	data class MenuDetailGetRoute(val menuId: String)
	
	@Location(GET_CALORIES_PREDICTION)
	class MenuCaloriesPredictionGetRoute
}

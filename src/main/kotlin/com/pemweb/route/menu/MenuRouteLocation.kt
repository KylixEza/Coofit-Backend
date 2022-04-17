package com.pemweb.route.menu

import com.pemweb.route.BASE_MENU
import com.pemweb.route.BASE_SELECTED_MENU
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
sealed class MenuRouteLocation {
	companion object {
		//POST
		const val POST_MENU = BASE_MENU
		//GET
		const val GET_TOP_MENU = "$BASE_MENU/top"
		//GET
		const val GET_MENU_DETAIL = BASE_SELECTED_MENU
		//GET
		const val GET_CALORIES_PREDICTION = "$BASE_MENU/prediction"
	}
	
	@Location(POST_MENU)
	class MenuPostRoute
	
	@Location(GET_TOP_MENU)
	class MenuTopGetRoute
	
	@Location(GET_MENU_DETAIL)
	data class MenuDetailGetRoute(val menuId: String)
	
	@Location(GET_CALORIES_PREDICTION)
	class MenuCaloriesPredictionGetRoute
}

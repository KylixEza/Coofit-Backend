package com.pemweb.route.user

import com.pemweb.route.BASE_SELECTED_USER
import com.pemweb.route.BASE_USER
import io.ktor.locations.*

@KtorExperimentalLocationsAPI
sealed class UserRouteLocation {
	companion object {
		//POST
		const val POST_USER = BASE_USER
		//GET
		const val GET_USER_ID = "$BASE_USER/login"
		//GET
		const val GET_USER_DETAIL = BASE_SELECTED_USER
		//PUT
		const val UPDATE_USER = BASE_SELECTED_USER
		//POST
		const val POST_FAVORITE = "$BASE_SELECTED_USER/favorite"
		//DELETE
		const val DELETE_FAVORITE = "$BASE_SELECTED_USER/favorite"
		//GET
		const val GET_FAVORITE = "$BASE_SELECTED_USER/favorite"
	}
	
	@Location(POST_USER)
	class UserPostRoute
	
	@Location(GET_USER_ID)
	class UserIdGetRoute
	
	@Location(GET_USER_DETAIL)
	data class UserDetailGetRoute(val uid: String)
	
	@Location(UPDATE_USER)
	data class UserUpdatePutRoute(val uid: String)
	
	@Location(POST_FAVORITE)
	data class UserFavoritePostRoute(val uid: String)
	
	@Location(DELETE_FAVORITE)
	data class UserFavoriteDeleteRoute(val uid: String)
	
	@Location(GET_FAVORITE)
	data class UserFavoriteGetRoute(val uid: String)
}

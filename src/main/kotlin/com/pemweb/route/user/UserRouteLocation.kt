package com.pemweb.route.user

sealed class UserRouteLocation {
	companion object {
		const val BASE_USER = "/user"
		const val SELECTED_USER = "$BASE_USER/{uid}"
		const val BASE_MENU = "/menu"
		const val SELECTED_MENU = "/menu/{menuId}"
		//POST
		const val POST_USER = BASE_USER
		//GET
		const val GET_IS_USER_EXIST = BASE_USER
		//GET
		const val GET_DETAIL_USER = SELECTED_USER
		//UPDATE
		const val UPDATE_USER = SELECTED_USER
		//POST
		const val POST_FAVORITE = "$SELECTED_USER/favorite"
		//DELETE
		const val DELETE_FAVORITE = "$SELECTED_USER/favorite"
		//GET
		const val GET_FAVORITE = "$SELECTED_USER/favorite"
		//POST
		const val POST_MENU = BASE_MENU
	}
}

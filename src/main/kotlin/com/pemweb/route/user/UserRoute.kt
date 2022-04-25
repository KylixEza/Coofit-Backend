package com.pemweb.route.user

import com.pemweb.controller.IUserController
import com.pemweb.helper.ResponseModelHelper.generalException
import com.pemweb.model.favorite.FavoriteBody
import com.pemweb.model.login.LoginBody
import com.pemweb.model.user.UserBody
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.locations.put
import io.ktor.request.*

@KtorExperimentalLocationsAPI
class UserRoute(
	private val controller: IUserController
) {
	
	private fun Route.postUser() {
		post<UserRouteLocation.UserPostRoute> {
			val body = try {
				call.receive<UserBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			controller.apply { call.addNewUser(body) }
		}
	}
	
	private fun Route.getIdOfUser() {
		get<UserRouteLocation.UserIdGetRoute> {
			val body = try {
				call.receive<LoginBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			
			controller.apply { call.getIdOfUser(body) }
		}
	}
	
	private fun Route.getUserDetail() {
		get<UserRouteLocation.UserDetailGetRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			} ?: ""
			
			controller.apply { call.getUserDetail(uid) }
		}
	}
	
	private fun Route.updateUser() {
		put<UserRouteLocation.UserUpdatePutRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			} ?: ""
			
			val body = try {
				call.receive<UserBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			}
			
			controller.apply { call.updateUser(uid, body) }
		}
	}
	
	private fun Route.postFavoriteUser() {
		post<UserRouteLocation.UserFavoritePostRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			} ?: ""
			
			val body = try {
				call.receive<FavoriteBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			controller.apply { call.addFavorite(uid, body) }
		}
	}
	
	private fun Route.deleteFavoriteUser() {
		delete<UserRouteLocation.UserFavoriteDeleteRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@delete
			} ?: ""
			
			val body = try {
				call.receive<FavoriteBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@delete
			}
			
			controller.apply { call.deleteFavorite(uid, body) }
		}
	}
	
	private fun Route.getFavoritesUser() {
		get<UserRouteLocation.UserFavoriteGetRoute> {
			
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			} ?: ""
			
			controller.apply { call.getAllFavoritesByUser(uid) }
		}
	}
	
	fun Route.initRoute() {
		this.apply {
			postUser()
			getIdOfUser()
			getUserDetail()
			updateUser()
			postFavoriteUser()
			deleteFavoriteUser()
			getFavoritesUser()
		}
	}
}
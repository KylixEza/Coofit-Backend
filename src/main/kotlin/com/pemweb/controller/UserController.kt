package com.pemweb.controller

import com.pemweb.helper.ResponseModelHelper.generalSuccess
import com.pemweb.data.ICoofitRepository
import com.pemweb.helper.ResponseModelHelper.generalException
import com.pemweb.model.favorite.FavoriteBody
import com.pemweb.model.login.LoginBody
import com.pemweb.model.user.UserBody
import io.ktor.application.*

class UserController(
	private val coofitRepository: ICoofitRepository
): IUserController {
	
	override suspend fun ApplicationCall.addNewUser(body: UserBody) {
		body.apply {
			try {
				if (!coofitRepository.isUserExist(username, password)) {
					this@addNewUser.generalSuccess("${body.username} successfully added") {
						coofitRepository.addNewUser(body)
					}
				} else {
					throw Exception("User already exist!")
				}
			} catch (e: Exception) {
				this@addNewUser.generalException(e)
			}
		}
	}
	
	override suspend fun ApplicationCall.getIdOfUser(body: LoginBody) =
		this.generalSuccess { coofitRepository.getIdOfUser(body.username, body.password) }
	
	override suspend fun ApplicationCall.getUserDetail(uid: String) =
		this.generalSuccess { coofitRepository.getUserDetail(uid) }
	
	override suspend fun ApplicationCall.updateUser(uid: String, body: UserBody) =
		this.generalSuccess("${body.username} successfully updated") {
			coofitRepository.updateUser(uid, body)
		}
	
	override suspend fun ApplicationCall.addFavorite(uid: String, body: FavoriteBody) =
		this.generalSuccess("Favorite successfully added") {
			coofitRepository.addFavorite(uid, body.menuId)
		}
	
	override suspend fun ApplicationCall.deleteFavorite(uid: String, body: FavoriteBody) =
		this.generalSuccess("Favorite successfully deleted") {
			coofitRepository.deleteFavorite(uid, body.menuId)
		}
	
	override suspend fun ApplicationCall.getAllFavoritesByUser(uid: String) =
		this.generalSuccess { coofitRepository.getAllFavoritesByUser(uid) }
}
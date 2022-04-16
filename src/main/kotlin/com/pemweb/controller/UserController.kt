package com.pemweb.controller

import com.oreyo.model.favorite.FavoriteBody
import com.pemweb.helper.ResponseModelHelper.generalSuccess
import com.pemweb.data.ICoofitRepository
import com.pemweb.model.login.LoginBody
import com.pemweb.model.user.UserBody
import io.ktor.application.*

class UserController(
	private val coofitRepository: ICoofitRepository
): IUserController {
	
	override suspend fun ApplicationCall.insertUser(body: UserBody) =
		this.generalSuccess("${body.name} successfully added") {
			coofitRepository.insertUser(body)
		}
	
	override suspend fun ApplicationCall.isUserExist(body: LoginBody) =
		this.generalSuccess { coofitRepository.isUserExist(body) }
	
	override suspend fun ApplicationCall.getUserDetail(uid: String) =
		this.generalSuccess { coofitRepository.getUserDetail(uid) }
	
	override suspend fun ApplicationCall.updateUser(uid: String, body: UserBody) =
		this.generalSuccess("${body.name} successfully updated") {
			coofitRepository.updateUser(uid, body)
		}
	
	override suspend fun ApplicationCall.addFavorite(uid: String, body: FavoriteBody) =
		this.generalSuccess("Favorite successfully added") {
			coofitRepository.addFavorite(uid, body)
		}
	
	override suspend fun ApplicationCall.deleteFavorite(uid: String, body: FavoriteBody) =
		this.generalSuccess("Favorite successfully deleted") {
			coofitRepository.deleteFavorite(uid, body)
		}
	
	override suspend fun ApplicationCall.getAllFavoritesByUser(uid: String) =
		this.generalSuccess { coofitRepository.getAllFavoritesByUser(uid) }
}
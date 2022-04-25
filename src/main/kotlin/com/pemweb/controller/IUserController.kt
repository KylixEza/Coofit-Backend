package com.pemweb.controller

import com.pemweb.model.favorite.FavoriteBody
import com.pemweb.model.login.LoginBody
import com.pemweb.model.user.UserBody
import io.ktor.application.*

interface IUserController {
	suspend fun ApplicationCall.addNewUser(body: UserBody)
	suspend fun ApplicationCall.getIdOfUser(body: LoginBody)
	suspend fun ApplicationCall.getUserDetail(uid: String)
	suspend fun ApplicationCall.updateUser(uid: String, body: UserBody)
	suspend fun ApplicationCall.addFavorite(uid: String, body: FavoriteBody)
	suspend fun ApplicationCall.deleteFavorite(uid: String, body: FavoriteBody)
	suspend fun ApplicationCall.getAllFavoritesByUser(uid: String)
}
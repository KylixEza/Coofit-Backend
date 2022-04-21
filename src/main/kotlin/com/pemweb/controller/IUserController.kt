package com.pemweb.controller

import com.oreyo.model.favorite.FavoriteBody
import com.pemweb.model.login.LoginBody
import com.pemweb.model.user.UserBody
import io.ktor.application.*

interface IUserController {
	suspend fun ApplicationCall.addNewUser(body: UserBody)
	suspend fun ApplicationCall.getUserDetail(uid: String)
	suspend fun ApplicationCall.updateUser(uid: String, body: UserBody)
	suspend fun ApplicationCall.addFavorite(uid: String, menuId: String)
	suspend fun ApplicationCall.deleteFavorite(uid: String, menuId: String)
	suspend fun ApplicationCall.getAllFavoritesByUser(uid: String)
}
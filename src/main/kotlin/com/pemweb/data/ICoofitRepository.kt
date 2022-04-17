package com.pemweb.data

import com.oreyo.model.favorite.FavoriteBody
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.user.UserResponse
import com.pemweb.model.login.LoginBody
import com.pemweb.model.login.LoginResponse
import com.pemweb.model.menu.MenuBody
import com.pemweb.model.menu.MenuLiteResponse
import com.pemweb.model.prediction.PredictionBody
import com.pemweb.model.prediction.PredictionResponse
import com.pemweb.model.user.UserBody

interface ICoofitRepository {
	suspend fun addNewUser(body: UserBody)
	suspend fun isUserExist(body: LoginBody): LoginResponse
	suspend fun getUserDetail(uid: String): UserResponse
	suspend fun updateUser(uid: String, body: UserBody)
	suspend fun addFavorite(uid: String, body: FavoriteBody)
	suspend fun deleteFavorite(uid: String, body: FavoriteBody)
	suspend fun getAllFavoritesByUser(uid: String): List<MenuLiteResponse>
	suspend fun addNewMenu(body: MenuBody)
	suspend fun getSomeMenus(): List<MenuLiteResponse>
	suspend fun getMenuDetail(menuId: String): MenuResponse
	suspend fun searchMenu(query: String): List<MenuLiteResponse>
	suspend fun getCaloriesPrediction(body: PredictionBody): PredictionResponse
}
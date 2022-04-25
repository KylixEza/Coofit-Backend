package com.pemweb.data

import com.oreyo.model.ingredient.IngredientBody
import com.pemweb.model.menu.MenuResponse
import com.oreyo.model.review.ReviewBody
import com.oreyo.model.step.StepBody
import com.oreyo.model.user.UserResponse
import com.pemweb.model.favorite.FavoriteBody
import com.pemweb.model.login.LoginResponse
import com.pemweb.model.menu.MenuBody
import com.pemweb.model.menu.MenuLiteResponse
import com.pemweb.model.prediction.PredictionBody
import com.pemweb.model.prediction.PredictionResponse
import com.pemweb.model.user.UserBody

interface ICoofitRepository {
	suspend fun addNewUser(body: UserBody) //done
	suspend fun getIdOfUser(username: String, password: String): LoginResponse //done
	suspend fun isUserExist(username: String, password: String): Boolean //done
	suspend fun getUserDetail(uid: String): UserResponse //done
	suspend fun updateUser(uid: String, body: UserBody) //done
	suspend fun addFavorite(uid: String, menuId: String) //done
	suspend fun deleteFavorite(uid: String, menuId: String) //done
	suspend fun getAllFavoritesByUser(uid: String): List<MenuLiteResponse>
	suspend fun addNewMenu(body: MenuBody) //done
	suspend fun addNewIngredient(menuId: String, body: IngredientBody) //done
	suspend fun addNewStep(menuId: String, body: StepBody) //done
	suspend fun addNewReview(menuId: String, body: ReviewBody) //done
	suspend fun getSomeMenus(): List<MenuLiteResponse> //done
	suspend fun getAllMenus(): List<MenuLiteResponse> //done
	suspend fun getMenuDetail(menuId: String): MenuResponse //done
	suspend fun searchMenu(query: String): List<MenuLiteResponse> //done
	suspend fun getCaloriesPrediction(body: PredictionBody): PredictionResponse
}
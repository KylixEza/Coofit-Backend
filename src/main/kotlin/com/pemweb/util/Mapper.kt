package com.pemweb.util

import com.oreyo.model.ingredient.IngredientResponse
import com.pemweb.model.menu.MenuResponse
import com.oreyo.model.review.ReviewResponse
import com.oreyo.model.step.StepResponse
import com.oreyo.model.user.UserResponse
import com.pemweb.data.table.*
import com.pemweb.model.login.LoginResponse
import com.pemweb.model.menu.MenuLiteResponse
import com.pemweb.model.prediction.PredictionResponse
import org.jetbrains.exposed.sql.Avg
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.alias

object Mapper {
	fun mapRowToUserResponse(row: ResultRow?): UserResponse? {
		if (row == null)
			return null
		
		return UserResponse(
			uid = row[UserTable.uid],
			username = row[UserTable.username],
			password = row[UserTable.password],
			address = row[UserTable.address],
			avatar = row[UserTable.avatar],
			coofitWallet = row[UserTable.coofitWallet],
			email = row[UserTable.email],
			phoneNumber = row[UserTable.phoneNumber],
			xp = row[UserTable.xp]
		)
	}
	
	fun mapRowToMenuLiteResponse(row: ResultRow?): MenuLiteResponse? {
		if (row == null)
			return null
		
		return MenuLiteResponse(
			menuId = row[MenuTable.menuId],
			difficulty = row[MenuTable.difficulty],
			calories = row[MenuTable.calories],
			cookTime = row[MenuTable.cookTime],
			image = row[MenuTable.image],
			rating = row[Avg(ReviewTable.rating, 1).alias("rating")]?.toDouble(),
			title = row[MenuTable.title]
		)
	}
	
	fun mapRowToMenuResponse(
		row: ResultRow?,
		ingredients: List<String>,
		steps: List<String>,
		reviews: List<ReviewResponse>
	): MenuResponse? {
		if (row == null)
			return null
		
		return MenuResponse(
			menuId = row[MenuTable.menuId],
			description = row[MenuTable.description],
			difficulty = row[MenuTable.difficulty],
			calories = row[MenuTable.calories],
			cookTime = row[MenuTable.cookTime],
			image = row[MenuTable.image],
			ingredients = ingredients,
			rating = row[Avg(ReviewTable.rating, 1).alias("rating")]?.toDouble() ?: 0.0,
			reviews = reviews,
			steps = steps,
			title = row[MenuTable.title],
		)
	}
	
	fun mapRowToIngredientResponse(row: ResultRow?): IngredientResponse? {
		if (row == null)
			return null
		
		return IngredientResponse(
			menuId = row[IngredientTable.menuId],
			ingredient = row[IngredientTable.ingredient]
		)
	}
	
	fun mapRowToStepResponse(row: ResultRow?): StepResponse? {
		if (row == null)
			return null
		
		return StepResponse(
			menuId = row[StepTable.menuId],
			step = row[StepTable.step]
		)
	}
	
	fun mapRowToReviewResponse(row: ResultRow?): ReviewResponse? {
		if (row == null)
			return null
		
		return ReviewResponse(
			menuId = row[ReviewTable.menuId],
			name = row[UserTable.username],
			avatar = row[UserTable.avatar],
			rating = row[ReviewTable.rating],
		)
	}
	
	fun mapRowToLoginResponse(row: ResultRow?): LoginResponse {
		val isExist = row?.get(UserTable.uid) != null
		
		return LoginResponse(
			isExist = isExist,
			uid =  if (isExist) row?.get(UserTable.uid) else null
		)
	}
	
	fun mapRowToPredictResponse(row: ResultRow?): PredictionResponse? {
		if (row == null)
			return null
		
		return PredictionResponse(
			food = row[MenuTable.title],
			calories = row[MenuTable.calories],
			accuracy = 0.0
		)
	}
}
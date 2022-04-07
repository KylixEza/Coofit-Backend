package com.pemweb.util

import com.oreyo.model.user.UserResponse
import com.pemweb.data.table.MenuTable
import com.pemweb.data.table.ReviewTable
import com.pemweb.data.table.UserTable
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
			price = row[MenuTable.price],
			rating = row[Avg(ReviewTable.rating, 1).alias("rating")]?.toDouble(),
			title = row[MenuTable.title]
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
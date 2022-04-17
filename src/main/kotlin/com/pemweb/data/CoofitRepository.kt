package com.pemweb.data

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.oreyo.model.favorite.FavoriteBody
import com.pemweb.model.user.UserBody
import com.pemweb.data.database.DatabaseFactory
import com.pemweb.data.table.FavoriteTable
import com.pemweb.data.table.MenuTable
import com.pemweb.data.table.ReviewTable
import com.pemweb.data.table.UserTable
import com.pemweb.model.login.LoginBody
import com.pemweb.model.menu.MenuBody
import com.pemweb.model.prediction.PredictionBody
import com.pemweb.model.prediction.PredictionResponse
import com.pemweb.util.Mapper
import org.jetbrains.exposed.sql.*
import org.nield.kotlinstatistics.toNaiveBayesClassifier
import java.util.*

class CoofitRepository(
	private val dbFactory: DatabaseFactory
): ICoofitRepository {
	override suspend fun addNewUser(body: UserBody) {
		dbFactory.dbQuery {
			UserTable.insert { table ->
				table[uid] = body.uid
				table[username] = body.username
				table[password] = body.password
				table[address] = body.address
				table[avatar] = body.avatar
				table[coofitWallet] = 0
				table[email] = body.email
				table[phoneNumber] = body.phoneNumber
				table[xp] = 0
			}
		}
	}
	
	override suspend fun isUserExist(body: LoginBody) = dbFactory.dbQuery {
		UserTable.select {
			UserTable.username.eq(body.username) and UserTable.password.eq(body.password)
		}.map {
			Mapper.mapRowToLoginResponse(it)
		}
	}.first()
	
	override suspend fun getUserDetail(uid: String) = dbFactory.dbQuery {
		UserTable.select {
			UserTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToUserResponse(it)
		}
	}.first()
	
	override suspend fun updateUser(uid: String, body: UserBody) {
		dbFactory.dbQuery {
			UserTable.update(
				where = {UserTable.uid.eq(uid)}
			) { table ->
				table[address] = body.address
				table[avatar] = body.avatar
				table[coofitWallet] = body.coofitWallet
				table[email] = body.email
				table[phoneNumber] = body.phoneNumber
				table[xp] = body.xp
			}
		}
	}
	
	override suspend fun addFavorite(uid: String, body: FavoriteBody) {
		dbFactory.dbQuery {
			FavoriteTable.insert { table ->
				table[FavoriteTable.uid] = uid
				table[menuId] = body.menuId
			}
		}
	}
	
	override suspend fun deleteFavorite(uid: String, body: FavoriteBody) {
		dbFactory.dbQuery {
			FavoriteTable.deleteWhere {
				FavoriteTable.uid.eq(uid) and FavoriteTable.menuId.eq(body.menuId)
			}
		}
	}
	
	override suspend fun getAllFavoritesByUser(uid: String) = dbFactory.dbQuery {
		MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}.join(FavoriteTable, JoinType.INNER) {
			FavoriteTable.menuId.eq(MenuTable.menuId)
		}.slice(
			MenuTable.menuId,
			MenuTable.difficulty,
			MenuTable.calories,
			MenuTable.cookTime,
			MenuTable.image,
			MenuTable.price,
			Avg(ReviewTable.rating, 1).alias("rating"),
			MenuTable.title,
		).select {
			FavoriteTable.uid.eq(uid)
		}.groupBy(MenuTable.menuId).mapNotNull {
			Mapper.mapRowToMenuLiteResponse(it)
		}
	}
	
	private fun getGeneralMenu(): FieldSet {
		return MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}
			.slice(
				MenuTable.menuId,
				MenuTable.benefit,
				MenuTable.description,
				MenuTable.difficulty,
				MenuTable.calories,
				MenuTable.cookTime,
				MenuTable.estimatedTime,
				MenuTable.image,
				MenuTable.ordered,
				MenuTable.price,
				Avg(ReviewTable.rating, 1).alias("rating"),
				MenuTable.title,
				MenuTable.category,
			)
	}
	
	override suspend fun addNewMenu(body: MenuBody) {
		dbFactory.dbQuery {
			MenuTable.insert { table ->
				table[menuId] = "MENU${NanoIdUtils.randomNanoId()}"
				table[benefit] = body.benefit
				table[category] = body.category
				table[description] = body.description
				table[difficulty] = body.difficulty
				table[calories] = body.calories
				table[cookTime] = body.cookTime
				table[estimatedTime] = body.estimatedTime
				table[image] = body.image
				table[ordered] = 0
				table[price] = body.price
				table[title] = body.title
			}
		}
	}
	
	override suspend fun getSomeMenus() = dbFactory.dbQuery {
		getGeneralMenu()
			.selectAll()
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuLiteResponse(it)
			}
	}.shuffled().take(20)
	
	
	override suspend fun getMenuDetail(menuId: String) = dbFactory.dbQuery {
		getGeneralMenu().select {
			MenuTable.menuId.eq(menuId)
		}
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}.first()
	
	override suspend fun searchMenu(query: String) = dbFactory.dbQuery {
		getGeneralMenu().select {
			LowerCase(MenuTable.title).like("%$query%".lowercase(Locale.getDefault()))
		}
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuLiteResponse(it)
			}
	}
	
	override suspend fun getCaloriesPrediction(body: PredictionBody): PredictionResponse {
		val menus = dbFactory.dbQuery {
			MenuTable.selectAll().mapNotNull {
				Mapper.mapRowToPredictResponse(it)
			}
		}
		
		val nbc = menus.toNaiveBayesClassifier(
			featuresSelector = { it.food.splitWords().toSet() },
			categorySelector = { it.calories }
		)
		
		val predictResult = nbc.predictWithProbability(body.food.splitWords().toSet())
		return PredictionResponse(
			body.food,
			predictResult?.category ?: -1,
			predictResult?.probability ?: -1.0
		)
	}
	
	private fun String.splitWords() =  split(Regex("\\s")).asSequence()
		.map { it.replace(Regex("[^A-Za-z]"),"").lowercase(Locale.getDefault()) }
		.filter { it.isNotEmpty() }
}
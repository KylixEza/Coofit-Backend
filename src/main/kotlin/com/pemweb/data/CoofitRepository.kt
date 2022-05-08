package com.pemweb.data

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.oreyo.model.ingredient.IngredientBody
import com.oreyo.model.review.ReviewBody
import com.oreyo.model.step.StepBody
import com.pemweb.model.user.UserBody
import com.pemweb.data.database.DatabaseFactory
import com.pemweb.data.table.*
import com.pemweb.model.login.LoginResponse
import com.pemweb.model.menu.MenuBody
import com.pemweb.model.prediction.PredictionResponse
import com.pemweb.util.Mapper
import io.ktor.util.*
import org.jetbrains.exposed.sql.*
import org.nield.kotlinstatistics.toNaiveBayesClassifier
import java.util.*

@InternalAPI
class CoofitRepository(
	private val dbFactory: DatabaseFactory
): ICoofitRepository {
	
	override suspend fun addNewUser(body: UserBody) {
		dbFactory.dbQuery {
			UserTable.insert { table ->
				table[uid] = "USER${NanoIdUtils.randomNanoId()}"
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
	
	override suspend fun getIdOfUser(username: String, password: String) = dbFactory.dbQuery {
		val results = UserTable.select {
			UserTable.username.eq(username) and UserTable.password.eq(password)
		}.count()
		
		if (results == 0L)
			return@dbQuery LoginResponse(isExist = false, uid = null)
		else {
			val uid = UserTable.select {
				UserTable.username.eq(username) and UserTable.password.eq(password)
			}.firstNotNullOf {
				it[UserTable.uid]
			}
			return@dbQuery LoginResponse(isExist = true, uid)
		}
	}
	
	override suspend fun isUserExist(username: String, password: String) = dbFactory.dbQuery {
		val users = UserTable.select {
			UserTable.username.eq(username)
		}.map {
			Mapper.mapRowToLoginResponse(it)
		}
		
		return@dbQuery users.isNotEmpty()
	}
	
	override suspend fun getUserDetail(uid: String) = dbFactory.dbQuery {
		UserTable.select {
			UserTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToUserResponse(it)
		}
	}.first()
	
	override suspend fun updateUser(uid: String, body: UserBody) {
		
		val currentUser = dbFactory.dbQuery {
			UserTable.select {
				UserTable.uid eq uid
			}.mapNotNull {
				Mapper.mapRowToUserResponse(it)
			}
		}.first()
		
		dbFactory.dbQuery {
			UserTable.update(
				where = {UserTable.uid.eq(uid)}
			) { table ->
				table[address] = body.address ?: currentUser.address
				table[avatar] = body.avatar ?: currentUser.avatar
				table[coofitWallet] = body.coofitWallet ?: currentUser.coofitWallet
				table[email] = body.email ?: currentUser.email
				table[phoneNumber] = body.phoneNumber ?: currentUser.phoneNumber
				table[xp] = body.xp ?: currentUser.xp
			}
		}
	}
	
	override suspend fun addFavorite(uid: String, menuId: String) {
		dbFactory.dbQuery {
			FavoriteTable.insert { table ->
				table[FavoriteTable.uid] = uid
				table[FavoriteTable.menuId] = menuId
			}
		}
	}
	
	override suspend fun deleteFavorite(uid: String, menuId: String) {
		dbFactory.dbQuery {
			FavoriteTable.deleteWhere {
				FavoriteTable.uid.eq(uid) and FavoriteTable.menuId.eq(menuId)
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
				MenuTable.description,
				MenuTable.difficulty,
				MenuTable.calories,
				MenuTable.cookTime,
				MenuTable.image,
				Avg(ReviewTable.rating, 1).alias("rating"),
				MenuTable.title,
			)
	}
	
	override suspend fun addNewMenu(body: MenuBody) {
		dbFactory.dbQuery {
			MenuTable.insert { table ->
				table[menuId] = "MENU${NanoIdUtils.randomNanoId()}"
				table[description] = body.description
				table[difficulty] = body.difficulty
				table[calories] = body.calories
				table[cookTime] = body.cookTime
				table[image] = body.image
				table[title] = body.title
			}
		}
	}
	
	override suspend fun addNewIngredient(menuId: String, body: IngredientBody) {
		dbFactory.dbQuery {
			IngredientTable.insert { table ->
				table[IngredientTable.menuId] = menuId
				table[ingredient] = body.ingredient
			}
		}
	}
	
	override suspend fun addNewStep(menuId: String, body: StepBody) {
		dbFactory.dbQuery {
			StepTable.insert { table ->
				table[this.menuId] = menuId
				table[step] = body.step
			}
		}
	}
	
	override suspend fun addNewReview(menuId: String, body: ReviewBody) {
		dbFactory.dbQuery {
			ReviewTable.insert { table ->
				table[uid] = body.uid
				table[this.menuId] = menuId
				table[rating] = body.rating
			}
		}
	}
	
	override suspend fun getSomeMenus() =
		getAllMenus().shuffled().take(20)
	
	override suspend fun getAllMenus() = dbFactory.dbQuery {
		getGeneralMenu().selectAll()
			.groupBy(MenuTable.menuId)
			.mapNotNull { Mapper.mapRowToMenuLiteResponse(it) }
	}
	
	override suspend fun getMenuDetail(menuId: String) = dbFactory.dbQuery {
		
		val ingredients = IngredientTable.select {
			IngredientTable.menuId eq menuId
		}.mapNotNull {
			it[IngredientTable.ingredient]
		}
		
		val steps = StepTable.select {
			StepTable.menuId eq menuId
		}.mapNotNull {
			it[StepTable.step]
		}
		
		val reviews = ReviewTable.join(UserTable, JoinType.FULL)
			.select {
				ReviewTable.menuId.eq(menuId)
			}.mapNotNull {
				Mapper.mapRowToReviewResponse(it)
			}
		
		getGeneralMenu().select {
			MenuTable.menuId.eq(menuId)
		}
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it, ingredients, steps, reviews)
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
	
	override suspend fun getCaloriesPrediction(food: String): PredictionResponse {
		val menus = dbFactory.dbQuery {
			MenuTable.selectAll().mapNotNull {
				Mapper.mapRowToPredictResponse(it)
			}
		}
		
		val nbc = menus.toNaiveBayesClassifier(
			featuresSelector = { it.food.splitWords().toSet() },
			categorySelector = { it.calories }
		)
		
		val predictResult = nbc.predictWithProbability(food.splitWords().toSet())
		return PredictionResponse(
			food,
			predictResult?.category ?: -1,
			predictResult?.probability ?: -1.0
		)
	}
	
	private fun String.splitWords() =  split(Regex("\\s")).asSequence()
		.map { it.replace(Regex("[^A-Za-z]"),"").lowercase(Locale.getDefault()) }
		.filter { it.isNotEmpty() }
}
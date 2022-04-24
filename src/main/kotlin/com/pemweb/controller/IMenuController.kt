package com.pemweb.controller

import com.oreyo.model.ingredient.IngredientBody
import com.oreyo.model.review.ReviewBody
import com.oreyo.model.step.StepBody
import com.pemweb.model.menu.MenuBody
import com.pemweb.model.prediction.PredictionBody
import io.ktor.application.*

interface IMenuController {
	suspend fun ApplicationCall.addNewMenu(body: MenuBody)
	suspend fun ApplicationCall.addNewIngredient(menuId: String, body: IngredientBody)
	suspend fun ApplicationCall.addNewStep(menuId: String, body: StepBody)
	suspend fun ApplicationCall.addNewReview(menuId: String, body: ReviewBody)
	suspend fun ApplicationCall.getSomeMenus()
	suspend fun ApplicationCall.getAllMenus()
	suspend fun ApplicationCall.getMenuDetail(menuId: String)
	suspend fun ApplicationCall.searchMenu(query: String)
	suspend fun ApplicationCall.getCaloriesPrediction(body: PredictionBody)
}
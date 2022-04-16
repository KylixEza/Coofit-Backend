package com.pemweb.controller

import com.pemweb.model.menu.MenuBody
import com.pemweb.model.prediction.PredictionBody
import io.ktor.application.*

interface IMenuController {
	suspend fun ApplicationCall.addNewMenu(body: MenuBody)
	suspend fun ApplicationCall.getSomeMenus()
	suspend fun ApplicationCall.getMenuDetail(menuId: String)
	suspend fun ApplicationCall.searchMenu(query: String)
	suspend fun ApplicationCall.getCaloriesPrediction(body: PredictionBody)
}
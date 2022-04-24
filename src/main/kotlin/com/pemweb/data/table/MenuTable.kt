package com.pemweb.data.table

import org.jetbrains.exposed.sql.Table

object MenuTable: Table() {
	
	override val tableName: String = "menu"
	
	val menuId = varchar("menu_id", 128)
	val description = varchar("description", 1024)
	val difficulty = varchar("difficulty", 24)
	val calories = integer("calories")
	val cookTime = integer("cook_time")
	val image = varchar("image", 64)
	val title = varchar("title", 128)
	
	override val primaryKey: PrimaryKey = PrimaryKey(menuId)
}